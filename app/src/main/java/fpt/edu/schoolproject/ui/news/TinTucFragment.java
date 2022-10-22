package fpt.edu.schoolproject.ui.news;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import fpt.edu.schoolproject.DOM.XMLDOMParser;
import fpt.edu.schoolproject.notification.MyNotification;
import fpt.edu.schoolproject.R;
import fpt.edu.schoolproject.adapter.LinkAdapter;


public class TinTucFragment extends Fragment {
    private ArrayList<String> arrTitle, arrLink;
    private LinkAdapter adapter;

    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tin_tuc, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        ListView lstTieuDe = view.findViewById(R.id.listViewNews);
        arrTitle = new ArrayList<>();
        arrLink = new ArrayList<>();

        progressBar.setVisibility(View.VISIBLE);
        new ReadRSS().execute("https://vnexpress.net/rss/giao-duc.rss");

        adapter = new LinkAdapter(getContext(), arrTitle);
        lstTieuDe.setAdapter(adapter);

        lstTieuDe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ConnectivityManager connectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if(!wifi.isConnected()){
                    MyNotification.checkSDK(getContext());
                    MyNotification.getNotification(getContext(), "Vui lòng kết nối wifi để sử dụng tính năng tin tức");
                    Toast.makeText(getContext(), "Mất kết nối", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getContext(), WebviewActivity.class);
                    intent.putExtra("linkNews", arrLink.get(i));
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    @SuppressLint("StaticFieldLeak")
    private class ReadRSS extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");
            String title = "";
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                title = parser.getValue(element, "title");
                arrTitle.add(title);
                String link = parser.getValue(element, "link");
                arrLink.add(link);
            }
            progressBar.setVisibility(View.GONE);
        }
    }
}