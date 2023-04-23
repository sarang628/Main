package com.sryang.main.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    /** 로컬 데이터베이스 제공 */
//    @Singleton
//    @Provides
//    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
//        return AppDatabase.getInstance(context)
//    }
}


@Module
@InstallIn(SingletonComponent::class)
abstract class AppRepositoryModule {
//    @Binds
//    abstract fun provideLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

//    @Binds
//    abstract fun provideNationRepository(nationRepositoryImpl: NationRepositoryImpl): NationRepository

//    @Binds
//    abstract fun provideMyReviewsRepository(myReviewsRepositoryImpl: MyReviewsRepositoryImpl): MyReviewsRepository

//    @Binds
//    abstract fun provideMyReviewRepository(myReviewRepositoryImpl: MyReviewRepositoryImpl): MyReviewRepository

//    @Binds
//    abstract fun provideFindRepository(findRepository: FindRepositoryImpl): FindRepository

//    @Binds
//    abstract fun provideFilterRepository(filterRepository: FilterRepositoryImpl): FilterRepository

//    @Binds
//    abstract fun provideFeedRepository(feedRepositoryImpl: FeedRepositoryImpl): FeedRepository
}