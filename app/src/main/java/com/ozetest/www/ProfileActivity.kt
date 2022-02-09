package com.ozetest.www

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import com.google.android.material.imageview.ShapeableImageView

class ProfileActivity : AppCompatActivity() {
    private lateinit var profileImage: ShapeableImageView
    private lateinit var usernameText: TextView
    private lateinit var linkText: TextView
    private lateinit var shareImage: ImageView
    private lateinit var circularProgressDrawable: CircularProgressDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val bundle = intent.extras
        val username: String? = bundle?.getString("userName")
        val imageUrl = bundle?.getString("imageString")
        val profileUrl: String? = bundle?.getString("profileUri")

        profileImage = findViewById(R.id.profile_image)
        usernameText = findViewById(R.id.username)
        linkText = findViewById(R.id.link)
        shareImage = findViewById(R.id.share_btn)


        // setting up the Progress bar to load profile photo
        circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 15f
        circularProgressDrawable.start()


        //Loading profile photo
        imageUrl.let {
            val imgUri = imageUrl?.toUri()?.buildUpon()?.scheme("https")?.build()
            profileImage.load(imgUri) {
                placeholder(circularProgressDrawable)
                error(R.drawable.ic_error_img)
            }
        }

        //setting Username
        usernameText.text = username

        //setting link text
        linkText.text = profileUrl

        //Clicking the Share button
        shareImage.setOnClickListener {
            val webIntent: Intent = Intent(Intent.ACTION_VIEW, Uri.parse(profileUrl))
            startActivity(webIntent)
        }


    }
}