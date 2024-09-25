package com.mati_tech.masa_english_learning.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.mati_tech.masa_english_learning.R
import com.example.masa_english_school.authenticator.SessionManager
import com.mati_tech.masa_english_learning.models.Material
import com.mati_tech.masa_english_learning.viewmodel.MaterialViewModel

class MaterialAdapter(
    private val context: Context,
    materialViewModel: MaterialViewModel?,
    private val sessionManager: SessionManager
) :
    RecyclerView.Adapter<MaterialAdapter.MaterialHolder>() {
    private var materials: MutableList<Material> = mutableListOf()

    private val materialViewModel: MaterialViewModel? = materialViewModel

    init {
        requireNotNull(materialViewModel) { "MaterialViewModel cannot be null" }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaterialHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.material_item, parent, false)
        return MaterialHolder(itemView)
    }

    override fun onBindViewHolder(holder: MaterialHolder, position: Int) {
        val currentMaterial: Material = materials[position]

        holder.textViewDescription.text = "File description: " + currentMaterial.filedescription
        holder.textViewFileName.text = "File name: " + getFileNameFromPath(currentMaterial.filename) // Display file name

        // Check the file_type of the uploaded file
        val fileType = recognize_file(currentMaterial.filePath)

        if (fileType == "image") {
            holder.pdf.visibility = View.INVISIBLE
            holder.image.visibility = View.VISIBLE
        }

        holder.itemView.setOnClickListener {
            val fileUri = Uri.parse(currentMaterial.filePath)
            openFileWithApp(context, fileUri)
        }

        if (sessionManager.role == "teacher") {
            holder.itemView.setOnLongClickListener {
                showDeleteConfirmationDialog(holder.adapterPosition)
                true
            }
        }
    }


    @SuppressLint("QueryPermissionsNeeded")
    private fun openFileWithApp(context: Context, fileUri: Uri) {
        val mimeType = getMimeType(fileUri.toString())

        val openIntent = Intent(Intent.ACTION_VIEW)
        openIntent.setDataAndType(fileUri, mimeType)
        //        openIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        openIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        context.startActivity(openIntent)
        // Check if there's an activity to handle the intent
//        if (openIntent.resolveActivity(context.getPackageManager()) != null) {
//            context.startActivity(openIntent);
//        } else {
//            Toast.makeText(context, "No application available to open this file", Toast.LENGTH_SHORT).show();
//        }
    }

    private fun getMimeType(url: String): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }


    private fun showDeleteConfirmationDialog(position: Int) {
        AlertDialog.Builder(context)
            .setTitle("Delete Material")
            .setMessage("Are you sure you want to delete this material?")
            .setPositiveButton("Yes") { dialog: DialogInterface?, which: Int ->
                val adapterPosition = position
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    deleteMaterial(adapterPosition)
                }
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun deleteMaterial(position: Int) {
        // Remove the material from the list and notify the adapter
        val material: Material = materials[position]


        materials.removeAt(position)
        notifyItemRemoved(position)

        // Remove the material from the database using ViewModel
//        materialViewModel?.delete(material)

        Toast.makeText(context, "Material deleted", Toast.LENGTH_SHORT).show()
    }

    // Helper method to extract file name from file path
    private fun getFileNameFromPath(filePath: String?): String {
        return filePath?.substring(filePath.lastIndexOf('/') + 1) ?: ""
    }

    override fun getItemCount(): Int {
        return materials.size
    }

//    fun setMaterials(materials: List<Material>) {
//        this.materials = materials
//        notifyDataSetChanged()
//    }

    inner class MaterialHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val textViewDescription: TextView =
            itemView.findViewById<TextView>(R.id.text_view_description)

        //            textViewFileUri = itemView.findViewById(R.id.text_view_file_uri);
        val textViewFileName: TextView = itemView.findViewById<TextView>(R.id.text_view_filename)
        val image: ImageView = itemView.findViewById<ImageView>(R.id.materials_image)
        val pdf: ImageView = itemView.findViewById<ImageView>(R.id.material_pdf)

        private val textViewFileUri: TextView? = null
    }

    fun recognize_file(file_path: String): String {
        return if (file_path.contains("/image") || file_path.contains(".jpg") || file_path.contains(
                ".jpeg"
            ) || file_path.contains(".png")
        ) {
            "image"
        } else {
            "pdf"
        }
    }

    companion object {
        private const val REQUEST_OPEN_DOCUMENT = 1
    }
}