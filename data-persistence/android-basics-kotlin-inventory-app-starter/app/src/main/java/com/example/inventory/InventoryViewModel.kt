package com.example.inventory

import androidx.lifecycle.*
import com.example.inventory.data.Item
import com.example.inventory.data.ItemDao
import kotlinx.coroutines.launch

class InventoryViewModel(private val itemDao: ItemDao) : ViewModel() {
    val allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()


    private fun insertItem(item: Item) {
        // 뷰모델이 소멸되면 코루틴 자동취소
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

    private fun getNewItemEntry(itemName: String, itemPrice: String, itemCount: String): Item {
        return Item(
            itemName = itemName,
            itemPrice = itemPrice.toDouble(),
            quantityInStock = itemCount.toInt()
        )
    }

    fun addNewItem(itemName: String, itemPrice: String, itemCount: String) {
        val newItem = getNewItemEntry(itemName, itemPrice, itemCount)
        insertItem(newItem)
    }

    fun isEntryValid(itemName: String, itemPrice: String, itemCount: String): Boolean {
        if (itemName.isBlank() || itemPrice.isBlank() || itemCount.isBlank()) {
            return false
        }
        return true
    }
}
class InventoryViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {

    /* 뷰모델 팩토리는 대부분 비슷하므로 다른 곳에서 재사용 가능*/
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        // 뷰모델 클레스부분만 수정
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            // 만들 뷰모델 반환
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}