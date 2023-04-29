package com.minshoe.detailmanagement

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button: Button = findViewById<Button>(R.id.getContact)
        button.setOnClickListener(View.OnClickListener {
            getPhoneContacts()
        })
    }


    //https://github.com/specialprojects-experiments/paperphone/blob/5e9aba7d67711962f8139011ecde0139fa11ea64/app/src/main/java/com/withgoogle/experiments/unplugged/ui/HomeActivity.kt#L178
    /// javaToKt String[] == arrayOf()
/***
 * 1. How to work with logcat: Make it standard :
 * 2. level:info  package:com.minshoe.detailmanagement
 * 3. List me the level info in Android
 * 4. nullable of android

 */


/***
*  Available columns: [last_time_contacted, phonetic_name, custom_ringtone, contact_status_ts, pinned, account_type, photo_id, photo_file_id, contact_status_res_package, video, contact_chat_capability, contact_status_icon, display_name_alt, sort_key_alt, in_visible_group, starred, contact_status_label, phonebook_label, is_user_profile, account_name, nickname, has_phone_number, display_name_source, company, phonetic_name_style, send_to_voicemail, lookup, contact_account_type, phonebook_label_alt, contact_last_updated_timestamp, photo_uri, phonebook_bucket, contact_status, display_name, sort_key, photo_thumb_uri, contact_presence, in_default_directory, times_contacted, _id, name_raw_contact_id, phonebook_bucket_alt]
* */

    fun getPhoneContacts() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), 0)
        }

        val contentResolver: ContentResolver = contentResolver
        val uri: Uri = ContactsContract.Contacts.CONTENT_URI

        val cursor = contentResolver.query(uri, null, null, null, null)
        Log.i("Content Provider", "Total # of Contact ::: ${cursor?.count}")


        if ((cursor?.count ?: 0) > 0) {
            val columnIndex:Int? = cursor?.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY)
            val columnIndex2: Int? = cursor?.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            while (cursor?.moveToNext() == true) {

               try {


                   val contactName: String = if((columnIndex?:-1) >0) cursor.getString(columnIndex?:1) else ""
                   val contactNumber: String = if((columnIndex?:-1) >0) cursor.getString(columnIndex?:1) else ""
                   Log.i("Content Provider", "Contact Name::: $contactName: $contactNumber")
               }catch (e: Exception ){
                   Log.i("Content Provider", "Contact Name::: Data NA")
               }
            }
        }
        cursor?.close()
    }
}