package com.simax.examassistant;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter {

    Context c;
    ArrayList<Model> users;
    String pdf;

    public CustomAdapter(Context c, ArrayList<Model> users) {
        this.c = c;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view==null)
        {
            view= LayoutInflater.from(c).inflate(R.layout.content,viewGroup,false);
        }

        TextView nameTxt= (TextView) view.findViewById(R.id.nameTxt);


        Model user= (Model) this.getItem(i);

        final String name=user.getTitle();
         pdf =user.getPdf();


        nameTxt.setText(name);


        //emailTxt.setText(pdf);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showPdf();
                //OPEN DETAIL ACTIVITY
                openDetailActivity(name,pdf);

            }
        });
        return view;
    }
    ////open activity
    private void openDetailActivity(String...details)
    {
        Intent i=new Intent(c,ItemDetailActivity.class);
        i.putExtra("NAME_KEY",details[0]);
        i.putExtra("PDF_KEY",details[1]);


        //c.startActivity(i);

        String extStorageDirectory = Environment.getExternalStorageDirectory()
                .toString();
        File folder = new File(extStorageDirectory, "pdf");
        folder.mkdir();
        File file = new File(folder, "Read.pdf");
        try {
            file.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Downloader.DownloadFile(pdf, file);
        File newFile = new File(Environment.getExternalStorageDirectory()+"/Mypdf/Read.pdf");

        //PackageManager packageManager = ItemListActivity.getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);

        //pdfView.fromUri(uri);
        intent.setDataAndType(uri, "application/pdf");
        c.startActivity(intent);
    }

    private void showPdf()
    {

    }


}
