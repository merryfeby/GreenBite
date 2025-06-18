package com.example.greenbite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [UserEntity::class, MenuEntity::class, CartEntity::class], version = 7)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun menuDao(): MenuDao
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "GreenBite"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                getInstance(context).menuDao().let { dao ->
                                    populateDatabase(dao)
                                }
                            }
                        }
                    })
                    .build()
                    .also { INSTANCE = it }
            }
        }

        private suspend fun populateDatabase(menuDao: MenuDao) {
//            getDummyMenuItems().forEach { menuItem ->
//                menuDao.insertMenu(menuItem)
//            }
        }

        private fun getDummyMenuItems(): List<MenuEntity> {
            return listOf(
                MenuEntity(
                    id_menu = 0,
                    nama_menu = "Green Salad Bowl",
                    price_menu = 55000,
                    image_menu = "green_salad_bowl",
                    desc_menu = "Fresh mixed greens with avocado, cucumber, and our special herb dressing",
                    category_menu = "Salad",
                    stock_menu = 15,
                    rating_menu = 5,
                ),
                MenuEntity(
                    id_menu = 0,
                    nama_menu = "Classic Caesar Salad",
                    price_menu = 48000,
                    image_menu = "caesar_salad",
                    desc_menu = "Romaine lettuce, parmesan, croutons, and creamy Caesar dressing",
                    category_menu = "Salad",
                    stock_menu = 10,
                    rating_menu = 4,
                ),
                MenuEntity(
                    id_menu = 0,
                    nama_menu = "Berry Blast Smoothie",
                    price_menu = 30000,
                    image_menu = "berry_smoothie",
                    desc_menu = "Mixed berries blended with almond milk and banana",
                    category_menu = "Smoothie",
                    stock_menu = 20,
                    rating_menu = 5,
                ),
                MenuEntity(
                    id_menu = 0,
                    nama_menu = "Tropical Green Smoothie",
                    price_menu = 32000,
                    image_menu = "tropical_green_smoothie",
                    desc_menu = "Spinach, pineapple, mango, and coconut water",
                    category_menu = "Smoothie",
                    stock_menu = 18,
                    rating_menu = 3,
                ),
                MenuEntity(
                    id_menu = 0,
                    nama_menu = "BBQ Tofu Wrap",
                    price_menu = 42000,
                    image_menu = "bbq_tofu_wrap",
                    desc_menu = "Grilled tofu, slaw, and BBQ sauce in a whole wheat wrap",
                    category_menu = "Wraps",
                    stock_menu = 12,
                    rating_menu = 5,
                ),
                MenuEntity(
                    id_menu = 0,
                    nama_menu = "Mediterranean Grain Bowl",
                    price_menu = 46000,
                    image_menu = "med_grain_bowl",
                    desc_menu = "Quinoa, chickpeas, cucumber, tomato, and lemon tahini dressing",
                    category_menu = "Lean-Bowl",
                    stock_menu = 14,
                    rating_menu = 3,
                )
            )
        }
    }
}