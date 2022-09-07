package com.sinamekidev.jettrivia.data

import java.lang.Exception

data class DataOrException<T,Boolean,Exception>(
    var data:T?=null,
    var loaded:Boolean?=null,
    var exception:Exception?=null
)
