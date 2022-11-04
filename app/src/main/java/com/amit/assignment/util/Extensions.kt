package com.amit.assignment.util

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import coil.load
import coil.request.ErrorResult
import coil.request.ImageRequest
import com.amit.assignment.R

fun ImageView.loadImageFull(url: String?) =
    load(url)

fun ImageView.loadImage(url: String?, progressBar: ProgressBar) =
    load(url) {
        placeholder(android.R.color.white)
        listener(onSuccess = { _, _ ->
            progressBar.visibility = View.GONE
        }, onError = { request: ImageRequest, throwable: ErrorResult ->
                setImageResource(R.drawable.ic_comics)
            })
    }
