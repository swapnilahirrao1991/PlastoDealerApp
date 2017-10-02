package com.plasto.dealerapp.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.plasto.dealerapp.R;
import com.plasto.dealerapp.retrofit.ApiClient;
import com.plasto.dealerapp.retrofit.ApiInterface;
import com.plasto.dealerapp.retrofit.dayendreport.request.DayEndReportRequest;
import com.plasto.dealerapp.retrofit.login.response.LoginResponse;
import com.plasto.dealerapp.retrofit.pdfresponse.Datum;
import com.plasto.dealerapp.retrofit.pdfresponse.PdfResponse;
import com.plasto.dealerapp.utils.CommonUtils;
import com.plasto.dealerapp.utils.Constants;
import com.plasto.dealerapp.utils.ProgressDialog;
import com.plasto.dealerapp.utils.SharedPrefUtils;
import com.google.gson.Gson;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainDashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Context context = MainDashboardActivity.this;
    public static FloatingActionButton fab;
    private SharedPrefUtils prefUtils = new SharedPrefUtils();
    LoginResponse userDetails;
    Gson gson = new Gson();
    private ApiInterface apiInterface;
    private ProgressDialog pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        pb = new ProgressDialog(this, "Loading...", R.color.colorPrimary);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_camera);
        userDetails = gson.fromJson(prefUtils.getFromPrefs(this, Constants.SP_KEY_USER_DETAILS_JSON, null), LoginResponse.class);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView = navigationView.getHeaderView(0);
        TextView navTitle = (TextView) hView.findViewById(R.id.nav_title);
        navTitle.setText(userDetails.getData().getName());

        TextView navSubTitle = (TextView) hView.findViewById(R.id.nav_subtitle);
        navSubTitle.setText(userDetails.getData().getEmail());

        TextView navMobile = (TextView) hView.findViewById(R.id.nav_mobile);
        navMobile.setText(userDetails.getData().getMobile());

        Log.d("TAG", "IMG:" + userDetails.getData().getProfileImage());
        String imgUrl = userDetails.getData().getProfileImage();

        final ImageView imgProfile = (ImageView) hView.findViewById(R.id.imageViewProfile);
        //"https://cdn0.iconfinder.com/data/icons/avatars-8/128/avatar-22-2-512.png"
        Glide.with(context).load(imgUrl).asBitmap().centerCrop().placeholder(R.drawable.ic_profile).into(new BitmapImageViewTarget(imgProfile) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imgProfile.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //  getMenuInflater().inflate(R.menu.main_dashboard, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_addDealer) {
            startActivity(new Intent(context, AddDealerActivity.class));

        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(context, ProfileActivity.class));
        } else if (id == R.id.nav_dayEndReport) {
            getDayEndReport();
        } else if (id == R.id.nav_logout) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        prefUtils.removeFromPrefs(context, SharedPrefUtils.KEY_IMEI);
        prefUtils.removeFromPrefs(context, Constants.SP_KEY_USER_DETAILS_JSON);
        prefUtils.removeFromPrefs(context, Constants.SP_KEY_USER_CITY_DETAILS_JSON);
        ActivityCompat.startActivity(this, new Intent(this, LoginActivity.class), null);
        finish();
    }


    private boolean convertToPdf(String outputPdfPath, PdfPTable table) {
        try {
            String title = "DAY END REPORT - " + CommonUtils.getCurrentDate() + " \nExecutive Name:" + userDetails.getData().getName() + "\n\n\n";
            // Create output file if needed
            File outputFile = new File(outputPdfPath);
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            Document document;
            document = new Document(PageSize.A4);

            PdfWriter.getInstance(document, new FileOutputStream(outputFile));

            document.open();
            //  document.add(new PdfPTable(6));
            document.addTitle(title);
            document.add(new Paragraph(title));
            document.add(new LineSeparator());
            document.add(table);
            document.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private void getDayEndReport() {
        pb.show();
        Call<PdfResponse> call = apiInterface.dayEndReport(new DayEndReportRequest(CommonUtils.getIMEI(MainDashboardActivity.this), userDetails.getData().getId(), userDetails.getData().getUserToken()));
        call.enqueue(new Callback<PdfResponse>() {
            @Override
            public void onResponse(Call<PdfResponse> call, Response<PdfResponse> response) {


                if (response.isSuccessful() && response.body().getData() != null && response.body().getResponse() && !response.body().getData().isEmpty()) {
                    generatePDFText(response.body().getData());
                } else if (response.body().getData().isEmpty()) {
                    Toast.makeText(MainDashboardActivity.this, "No data for day end report", Toast.LENGTH_SHORT).show();
                } else
                    CommonUtils.showSnackBar(MainDashboardActivity.this, response.body().getMessage());
                pb.dismiss();
            }

            @Override
            public void onFailure(Call<PdfResponse> call, Throwable t) {
                pb.dismiss();
            }
        });
    }

    //sachinjoshi6811
    private void generatePDFText(List<Datum> data) {

        if (data.isEmpty()) {
            return;
        }

        PdfPTable table = new PdfPTable(130);


        // table.getDefaultCell().setColspan(10);
        //PdfPCell cell;
        table.setTotalWidth(PageSize.A4.getWidth() - 20);

        table.setLockedWidth(true);
        table.addCell(getCell(" ", 4, true));
        table.addCell(getCell("DEALER NAME", 20, true));
        table.addCell(getCell("MOBILE NO.", 15, true));
        table.addCell(getCell("TIME", 15, true));
        table.addCell(getCell("AREA", 15, true));
        table.addCell(getCell("LAT-LONG", 15, true));
        table.addCell(getCell("GOOGLE AREA", 20, true));
        table.addCell(getCell("DESCRIPTION", 26, true));
        int position = 1;

        for (Datum each : data) {
            table.addCell(getCell(position + "", 4, false));
            table.addCell(getCell(each.getDelaerName(), 20, false));
            table.addCell(getCell(each.getDelaerMobile(), 15, false));
            table.addCell(getCell(each.getTime(), 15, false));
            table.addCell(getCell(each.getAreaName(), 15, false));
            table.addCell(getCell(each.getLatLng(), 15, false));
            table.addCell(getCell(each.getGoogleAddress(), 20, false));
            table.addCell(getCell(each.getDescData(), 26, false));
            position++;
        }


        File folder = new File(Environment.getExternalStorageDirectory() + "/Plasto");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
        // Output file
        String outputPath = folder.getAbsolutePath() + "/plasto_" + System.currentTimeMillis() + ".pdf";

        // Run conversion
        final boolean result = convertToPdf(outputPath, table);
        if (result) {
            open(outputPath);
            CommonUtils.showSnackBar(MainDashboardActivity.this, "PDF created in Plasto Directory");
        } else
            Toast.makeText(this, "PDF creating failed", Toast.LENGTH_SHORT).show();
    }

    private PdfPCell getCell(String s, int span, boolean header) {
        PdfPCell cell = new PdfPCell(new Phrase(s));
        cell.setColspan(span);
        cell.setPadding(5.0f);
        if (header) {
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(new BaseColor(140, 221, 8));
        }
        return cell;
    }


    public void open(final String filePath) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainDashboardActivity.this);
        builder.setTitle("Day End Report");
        builder.setMessage("Pdf for day end report.");


        //Button One : Yes
        builder.setPositiveButton("Open", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                File file = new File(filePath);
                Uri path = Uri.fromFile(file);
                Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pdfOpenintent.setDataAndType(path, "application/pdf");
                try {
                    startActivity(pdfOpenintent);
                } catch (ActivityNotFoundException e) {

                }
            }
        });


        //Button Two : No
        builder.setNegativeButton("Share", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Sharing!! Please wait...", Toast.LENGTH_SHORT).show();


                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                Uri screenshotUri = Uri.parse(filePath);
                sharingIntent.setType("*/*");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                startActivity(Intent.createChooser(sharingIntent, "Share Day End Report"));

                dialog.cancel();
            }
        });


        //Button Three : Neutral
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        AlertDialog diag = builder.create();
        diag.show();
    }
}
