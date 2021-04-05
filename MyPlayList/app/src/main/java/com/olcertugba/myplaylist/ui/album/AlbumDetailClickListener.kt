package com.olcertugba.myplaylist.ui.album

import android.view.View

interface AlbumDetailClickListener{

    fun onItemClickListener(v: View, pos:Int, item:Any)
}