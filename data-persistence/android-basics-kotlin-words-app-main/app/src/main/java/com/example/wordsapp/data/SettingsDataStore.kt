package com.example.wordsapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


private const val LAYOUT_PREFERENCES_NAME = "layout_preferences"
// dataStore 안에 Flow<Preferences> 생산자가 포함되어 있음
private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
    name = LAYOUT_PREFERENCES_NAME
)
class SettingsDataStore(context: Context) {
    private val IS_LINEAR_LAYOUT_MANAGER = booleanPreferencesKey("is_linear_layout_manager")

    // 트랜잭션은 Dispacter.IO 에서 작동하므로 suspend 함수로 생성
    suspend fun saveLayoutToPreferencesStore(isLinearLayoutManager: Boolean, context: Context) {
        //  edit : datastore 의 데이터를 트랜잭션 방식으로 업데이트(생산)
        context.dataStore.edit { preferences ->
            preferences[IS_LINEAR_LAYOUT_MANAGER] = isLinearLayoutManager
        }
    }

    // datastore 가 Preferences 노출하지않고 Boolean 만 노출시키게함
    val preferenceFlow: Flow<Boolean> = context.dataStore.data
            // 생산자 단계의 에러를 잡음 (파일에서의 읽고 쓰기중의 오류)
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences()) // 에러시 생산자 단계의 flow 를 비움
            } else {
                throw it // 입출력 이외의 오류시 예외 다시발생
            }
        } // 중간 연산자
        .map { preferences ->
            // 처음에는 값이 비어있으므로 기본 true 반환
            preferences[IS_LINEAR_LAYOUT_MANAGER] ?: true
        }
}