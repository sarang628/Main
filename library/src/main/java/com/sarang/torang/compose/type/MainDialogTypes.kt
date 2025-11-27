package com.sarang.torang.compose.type

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

typealias ReportBottomSheetType = @Composable (Int, onReported: () -> Unit) -> Unit
typealias ShareBottomSheet      = @Composable (onClose: () -> Unit) -> Unit
typealias CommentBottomSheet    = @Composable (reviewId: Int?) -> Unit
typealias RestaurantBottomSheet = @Composable ( @Composable () -> Unit ) -> Unit
typealias MenuBottomSheet       = @Composable (reviewId: Int, onClose: () -> Unit, onReport: (Int) -> Unit, onDelete: (Int) -> Unit, onEdit: (Int) -> Unit) -> Unit


val LocalReportBottomSheetType = compositionLocalOf<ReportBottomSheetType>(){
    @Composable { _,_ ->

    }
}

val LocalShareBottomSheet = compositionLocalOf<ShareBottomSheet>(){
    @Composable {

    }
}

val LocalCommentBottomSheet = compositionLocalOf<CommentBottomSheet>(){
    @Composable {
        Log.d("__LocalCommentBottomSheet","LocalCommentBottomSheet does not set")
    }
}

val LocalRestaurantBottomSheet = compositionLocalOf<RestaurantBottomSheet>(){
    @Composable {
        it.invoke()
    }
}

val LocalMenuBottomSheet = compositionLocalOf<MenuBottomSheet>(){
    @Composable {_,_,_,_,_->
    }
}