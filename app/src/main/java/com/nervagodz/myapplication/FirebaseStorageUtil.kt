package com.nervagodz.myapplication

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object FirebaseStorageUtil {
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference
    private const val IMAGES_FOLDER = "waste_images"

    /**
     * Uploads an image to Firebase Storage
     * @param imageUri The URI of the image to upload
     * @return The download URL of the uploaded image
     */
    suspend fun uploadImage(imageUri: Uri): String = suspendCoroutine { continuation ->
        val imageFileName = "${UUID.randomUUID()}.jpg"
        val imageRef = storageRef.child("$IMAGES_FOLDER/$imageFileName")

        imageRef.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot ->
                // Get the download URL
                imageRef.downloadUrl
                    .addOnSuccessListener { downloadUri ->
                        continuation.resume(downloadUri.toString())
                    }
                    .addOnFailureListener { e ->
                        continuation.resumeWithException(e)
                    }
            }
            .addOnFailureListener { e ->
                continuation.resumeWithException(e)
                Log.e("FirebaseStorage", "Error uploading image", e)
            }
    }

    /**
     * Deletes an image from Firebase Storage
     * @param imageUrl The URL of the image to delete
     */
    fun deleteImage(imageUrl: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val imageRef: StorageReference = storage.getReferenceFromUrl(imageUrl)
        imageRef.delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure(e)
                Log.e("FirebaseStorage", "Error deleting image", e)
            }
    }
}
