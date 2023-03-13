package com.guillem.sample_app_rickandmorty.commonsui.compose

interface OnSystemBackPressed {

    /**
     * Performs an action when the system back is called.
     * @return True if the system back is overridden and does not have to be performed, or false
     * if the default back has to be performed
     */
    fun onBackHandled(): Boolean = false
}
