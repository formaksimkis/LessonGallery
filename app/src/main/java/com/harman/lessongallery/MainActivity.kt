package com.harman.lessongallery

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val GALLERY_REQUEST = 1
    val SAVED_RESULT = "saveResult"
    var imageURI: Uri? = null
    var angle = 0.0f
    val mImageAddress = "http://developer.alexanderklimov.ru/android/images/android_cat.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            imageURI = savedInstanceState.getParcelable(SAVED_RESULT)
            image.setImageURI(imageURI)
        }
        buttonAdd.setOnClickListener {

            val photoPickerIntent: Intent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.setType("image/*")
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST)
        }
        buttonAdd.setOnLongClickListener {
            Glide
                .with(this)
                .load(mImageAddress)
                .into(image)

            true

        }

        buttonRight.setOnClickListener {
            angle += 90
            image.animate().rotation(angle)
        }
        buttonLeft.setOnClickListener {
            angle -= 90
            image.animate().rotation(angle)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, imageReturnedIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)
        when (requestCode) {
            GALLERY_REQUEST -> if (resultCode == Activity.RESULT_OK) {
                val selectedImageURI: Uri = imageReturnedIntent?.data!!
                imageURI = selectedImageURI
                image.setImageURI(selectedImageURI)
            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        imageURI = savedInstanceState.getParcelable(SAVED_RESULT)
        image.setImageURI(imageURI)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(SAVED_RESULT, imageURI)
    }
}

