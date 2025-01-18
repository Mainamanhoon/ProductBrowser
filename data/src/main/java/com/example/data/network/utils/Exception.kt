package com.example.data.network.utils

import java.io.IOException

class ApiException(message :String):IOException(message)

class NoInternalException(message:String):IOException(message)