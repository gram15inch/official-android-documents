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
    private fun updateItem(item: Item) {
        viewModelScope.launch {
            itemDao.update(item)
        }
    }
    fun deleteItem(item: Item) {
        viewModelScope.launch {
            itemDao.delete(item)
        }
    }
    private fun getNewItemEntry(itemName: String, itemPrice: String, itemCount: String): Item {
        return Item(
            itemName = itemName,
            itemPrice = itemPrice.toDouble(),
            quantityInStock = itemCount.toInt()
        )
    }
    private fun getUpdatedItemEntry(
        itemId: Int,
        itemName: String,
        itemPrice: String,
        itemCount: String
    ): Item {
        return Item(
            id = itemId,
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

    fun retrieveItem(id: Int): LiveData<Item> {
        return itemDao.getItem(id).asLiveData()
    }

    fun sellItem(item: Item) {
        if (item.quantityInStock > 0) {
            val newItem = item.copy(quantityInStock = item.quantityInStock - 1)
            updateItem(newItem)
        }
    }

    fun updateItem(
        itemId: Int,
        itemName: String,
        itemPrice: String,
        itemCount: String
    ) {
        val updatedItem = getUpdatedItemEntry(itemId, itemName, itemPrice, itemCount)
        updateItem(updatedItem)
    }
    fun isStockAvailable(item: Item): Boolean {
        return (item.quantityInStock > 0)
    }
}

/* 뷰모델 팩토리는 대부분 비슷하므로 다른 곳에서 재사용 가능*/
class InventoryViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {

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