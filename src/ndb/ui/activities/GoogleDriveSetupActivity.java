package ndb.ui.activities;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

public class GoogleDriveSetupActivity extends Activity
{
  static final int REQUEST_ACCOUNT_PICKER = 1;
  static final int REQUEST_AUTHORIZATION = 2;
  static final int CAPTURE_IMAGE = 3;

  private static android.net.Uri fileUri;
  private static Drive service;
  private GoogleAccountCredential credential;

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    credential = GoogleAccountCredential.usingOAuth2(this, DriveScopes.DRIVE);
    startActivityForResult(credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
  }

  @Override
  protected void onActivityResult(final int requestCode, final int resultCode, final Intent data)
  {
    switch (requestCode)
    {
      case REQUEST_ACCOUNT_PICKER:
        if (resultCode == RESULT_OK && data != null && data.getExtras() != null)
        {
          String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
          if (accountName != null)
          {
            credential.setSelectedAccountName(accountName);
            service = getDriveService(credential);
            startCameraIntent();
          }
        }
        break;
      case REQUEST_AUTHORIZATION:
        if (resultCode == Activity.RESULT_OK)
        {
          saveFileToDrive();
        } else
        {
          startActivityForResult(credential.newChooseAccountIntent(), REQUEST_ACCOUNT_PICKER);
        }
        break;
      case CAPTURE_IMAGE:
        if (resultCode == Activity.RESULT_OK)
        {
          saveFileToDrive();
        }
    }
  }

  private void startCameraIntent()
  {
    String mediaStorageDir = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES).getPath();
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
    fileUri = Uri.fromFile(new java.io.File(mediaStorageDir + java.io.File.separator + "IMG_"
            + timeStamp + ".jpg"));

    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
    startActivityForResult(cameraIntent, CAPTURE_IMAGE);
  }

  private void saveFileToDrive()
  {
    Thread t = new Thread(new Runnable()
    {
      @Override
      public void run()
      {
        try
        {
          // File's binary content
          java.io.File fileContent = new java.io.File(fileUri.getPath());
          FileContent mediaContent = new FileContent("image/jpeg", fileContent);

          // File's metadata.
          File body = new File();
          body.setTitle(fileContent.getName());
          body.setMimeType("image/jpeg");

          File file = service.files().insert(body, mediaContent).execute();
          if (file != null)
          {
            showToast("Photo uploaded: " + file.getTitle());
            startCameraIntent();
          }
        } catch (UserRecoverableAuthIOException e)
        {
          startActivityForResult(e.getIntent(), REQUEST_AUTHORIZATION);
        } catch (IOException e)
        {
          e.printStackTrace();
        }
      }
    });
    t.start();
  }

  private Drive getDriveService(GoogleAccountCredential credential)
  {
    return new Drive.Builder(AndroidHttp.newCompatibleTransport(), new GsonFactory(), credential)
            .build();
  }

  public void showToast(final String toast)
  {
    runOnUiThread(new Runnable()
    {
      @Override
      public void run()
      {
        Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
      }
    });
  }
}


//
//
//
//////////////////////////////////////////////////////////
////String folderId = "0BwI6rRcqw8ZURlpleEVsRUhod0U";
////
////File fileContent = new java.io.File(Environment.getExternalStorageDirectory() + "/DCIM/Camera/" + Files[count].getName());
////
////FileContent mediaContent = new FileContent("image/jpeg", fileContent);
////
////com.google.api.services.drive.model.File body = new com.google.api.services.drive.model.File();
////
////body.setTitle(fileContent.getName());
////body.setMimeType("image/jpeg");
////
////body.setParents(Arrays.asList(new ParentReference().setId(folderId)));
////
////com.google.api.services.drive.model.File file = drive.files().insert(body, mediaContent
////
////////////////////////////////////////////////////////////
//
//
//
//
//
//
//
//
//
