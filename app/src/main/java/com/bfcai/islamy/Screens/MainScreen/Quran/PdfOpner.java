package com.bfcai.islamy.Screens.MainScreen.Quran;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.bfcai.islamy.MainActivity;
import com.bfcai.islamy.R;
import com.github.barteksc.pdfviewer.PDFView;

public class PdfOpner extends AppCompatActivity {
    PDFView mypdfviewer;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_opner);
        toolbar = findViewById(R.id.quranPdfToolbar);
        setSupportActionBar(toolbar);

        String item = getIntent().getStringExtra("item");
        TextView title = findViewById(R.id.suarhTitle);
        title.setText(item+"");
        mypdfviewer=(PDFView)findViewById(R.id.pdfviewer);

        // بجيب الرقم بتاع item في listview لي كنت خزنته وبعرفه انه العناصر ترتيبها بيبدء العد من 0
        int num = getIntent().getIntExtra("num",0);

        // زودت علي العنصر بتاع كل item واحد عشان ترتيب الملفات pdf علي اليمين بيبدء من 001
        num= num+1;

        /*
                هنا اهم جزئية العدد هيقعد يزيد
                فبعد ما يزيد عن 10 اسم pdf هيبقي 0011
                وفوق 100 هيبقي 00101
                فعشان كدا عملت حالة if ده
        */
        if(num <10){
            mypdfviewer.fromAsset("00"+ num +".pdf").load();
        }
        else if(item.equals("يونس")){
            mypdfviewer.fromAsset("010.pdf").load();
        }
        else if (num > 10 && num< 100){
            mypdfviewer.fromAsset("0"+ num +".pdf").load();
        } else {
            mypdfviewer.fromAsset( num +".pdf").load();
        }

   /*     if (getitem.equals("Faitha"))
        {
            mypdfviewer.fromAsset("001.pdf").load();
        } if (getitem.equals("Al-Baqarah"))
        {
            mypdfviewer.fromAsset("002.pdf").load();
        } if (getitem.equals("Al Imran"))
        {
            mypdfviewer.fromAsset("003.pdf").load();
        } if (getitem.equals("An-Nisa"))
        {
            mypdfviewer.fromAsset("004.pdf").load();
        } if (getitem.equals("AAl-Ma'idah"))
        {
            mypdfviewer.fromAsset("005.pdf").load();
        } if (getitem.equals("Al-An'am"))
        {
            mypdfviewer.fromAsset("006.pdf").load();
        } if (getitem.equals("Al-A'raf"))
        {
            mypdfviewer.fromAsset("007.pdf").load();
        } if (getitem.equals("Al-Anfal"))
        {
            mypdfviewer.fromAsset("008.pdf").load();
        } if (getitem.equals("At-Tawbah"))
        {
            mypdfviewer.fromAsset("009.pdf").load();
        } if (getitem.equals("Yunus"))
        {
            mypdfviewer.fromAsset("010.pdf").load();
        } if (getitem.equals("Hud"))
        {
            mypdfviewer.fromAsset("011.pdf").load();
        } if (getitem.equals("Yusuf"))
        {
            mypdfviewer.fromAsset("012.pdf").load();
        } if (getitem.equals("Ar-Ra'd"))
        {
            mypdfviewer.fromAsset("013.pdf").load();
        } if (getitem.equals("Ibrahim"))
        {
            mypdfviewer.fromAsset("014.pdf").load();
        } if (getitem.equals("Al-Hijr"))
        {
            mypdfviewer.fromAsset("015.pdf").load();
        } if (getitem.equals("An-Nahl"))
        {
            mypdfviewer.fromAsset("016.pdf").load();
        } if (getitem.equals("Al-Isra"))
        {
            mypdfviewer.fromAsset("017.pdf").load();
        } if (getitem.equals("Al-Kahf"))
        {
            mypdfviewer.fromAsset("018.pdf").load();
        } if (getitem.equals("Maryam"))
        {
            mypdfviewer.fromAsset("019.pdf").load();
        } if (getitem.equals("Ta-Ha"))
        {
            mypdfviewer.fromAsset("020.pdf").load();
        } if (getitem.equals("Al-Anbiya"))
        {
            mypdfviewer.fromAsset("021.pdf").load();
        } if (getitem.equals("Al-Hajj"))
        {
            mypdfviewer.fromAsset("022.pdf").load();
        } if (getitem.equals("Al-Mu'minun"))
        {
            mypdfviewer.fromAsset("023.pdf").load();
        } if (getitem.equals("An-Nur"))
        {
            mypdfviewer.fromAsset("024.pdf").load();
        } if (getitem.equals("Al-Furqan"))
        {
            mypdfviewer.fromAsset("025.pdf").load();
        } if (getitem.equals("Ash-Shu'ara"))
        {
            mypdfviewer.fromAsset("026.pdf").load();
        } if (getitem.equals("An-Naml"))
        {
            mypdfviewer.fromAsset("027.pdf").load();
        } if (getitem.equals("Al-Qasas"))
        {
            mypdfviewer.fromAsset("028.pdf").load();
        } if (getitem.equals("Al-Ankabut"))
        {
            mypdfviewer.fromAsset("029.pdf").load();
        } if (getitem.equals("Ar-Rum"))
        {
            mypdfviewer.fromAsset("030.pdf").load();
        } if (getitem.equals("Luqmaan"))
        {
            mypdfviewer.fromAsset("031.pdf").load();
        } if (getitem.equals("As-Sajda"))
        {
            mypdfviewer.fromAsset("032.pdf").load();
        } if (getitem.equals("Al-Ahzaab"))
        {
            mypdfviewer.fromAsset("033.pdf").load();
        } if (getitem.equals("Saba"))
        {
            mypdfviewer.fromAsset("034.pdf").load();
        } if (getitem.equals("Faatir"))
        {
            mypdfviewer.fromAsset("035.pdf").load();
        } if (getitem.equals("Ya-Sin"))
        {
            mypdfviewer.fromAsset("036.pdf").load();
        } if (getitem.equals("As-Saaffaat"))
        {
            mypdfviewer.fromAsset("037.pdf").load();
        } if (getitem.equals("Saad"))
        {
            mypdfviewer.fromAsset("038.pdf").load();
        } if (getitem.equals("Az-Zumar"))
        {
            mypdfviewer.fromAsset("039.pdf").load();
        } if (getitem.equals("Ghafir"))
        {
            mypdfviewer.fromAsset("040.pdf").load();
        } if (getitem.equals("Fussilat"))
        {
            mypdfviewer.fromAsset("041.pdf").load();
        } if (getitem.equals("Ash_Shooraa"))
        {
            mypdfviewer.fromAsset("042.pdf").load();
        } if (getitem.equals("Az-Zukhruf"))
        {
            mypdfviewer.fromAsset("043.pdf").load();
        } if (getitem.equals("Ad-Dukhaan"))
        {
            mypdfviewer.fromAsset("044.pdf").load();
        } if (getitem.equals("Al-Jaathiyah"))
        {
            mypdfviewer.fromAsset("045.pdf").load();
        } if (getitem.equals("Al-Ahqaaf"))
        {
            mypdfviewer.fromAsset("046.pdf").load();
        } if (getitem.equals("Muhammad"))
        {
            mypdfviewer.fromAsset("047.pdf").load();
        } if (getitem.equals("Al-Fath"))
        {
            mypdfviewer.fromAsset("048.pdf").load();
        } if (getitem.equals("Al-Fatiha"))
        {
            mypdfviewer.fromAsset("049.pdf").load();
        } if (getitem.equals("Al-Hujuraat"))
        {
            mypdfviewer.fromAsset("050.pdf").load();
        } if (getitem.equals("Qaaf"))
        {
            mypdfviewer.fromAsset("051.pdf").load();
        }if (getitem.equals("Adh-Dhaariyaat"))
        {
            mypdfviewer.fromAsset("052.pdf").load();
        }if (getitem.equals("At-Toor"))
        {
            mypdfviewer.fromAsset("053.pdf").load();
        }if (getitem.equals("An-Najm"))
        {
            mypdfviewer.fromAsset("054.pdf").load();
        }if (getitem.equals("Al-Qamar"))
        {
            mypdfviewer.fromAsset("055.pdf").load();
        }if (getitem.equals("Ar-Rahman"))
        {
            mypdfviewer.fromAsset("056.pdf").load();
        }if (getitem.equals("Al-Waqi'a"))
        {
            mypdfviewer.fromAsset("057.pdf").load();
        }if (getitem.equals("Al-Hadeed"))
        {
            mypdfviewer.fromAsset("058.pdf").load();
        }if (getitem.equals("Al-Hashr"))
        {
            mypdfviewer.fromAsset("059.pdf").load();
        }if (getitem.equals("Al-Mumtahanah"))
        {
            mypdfviewer.fromAsset("060.pdf").load();
        }if (getitem.equals("As-Saff"))
        {
            mypdfviewer.fromAsset("061.pdf").load();
        }if (getitem.equals("Al-Jumu'ah"))
        {
            mypdfviewer.fromAsset("062.pdf").load();
        }if (getitem.equals("Al-Munafiqoon"))
        {
            mypdfviewer.fromAsset("063.pdf").load();
        }if (getitem.equals("At-Taghabun"))
        {
            mypdfviewer.fromAsset("064.pdf").load();
        }if (getitem.equals("At-Talaq"))
        {
            mypdfviewer.fromAsset("065.pdf").load();
        }if (getitem.equals("At-Tahreem"))
        {
            mypdfviewer.fromAsset("066.pdf").load();
        }if (getitem.equals("Al-Mulk"))
        {
            mypdfviewer.fromAsset("067.pdf").load();
        }if (getitem.equals("Al-Qalam"))
        {
            mypdfviewer.fromAsset("068.pdf").load();
        }if (getitem.equals("Al-Haaqqa"))
        {
            mypdfviewer.fromAsset("069.pdf").load();
        }if (getitem.equals("Al-Ma'aarij"))
        {
            mypdfviewer.fromAsset("070.pdf").load();
        }if (getitem.equals("Nooh"))
        {
            mypdfviewer.fromAsset("071.pdf").load();
        }if (getitem.equals("Al-Jinn"))
        {
            mypdfviewer.fromAsset("072.pdf").load();
        }if (getitem.equals("Al-Muzzammil"))
        {
            mypdfviewer.fromAsset("073.pdf").load();
        }if (getitem.equals("Al-Muddaththir"))
        {
            mypdfviewer.fromAsset("074.pdf").load();
        }if (getitem.equals("Al-Qiyamah"))
        {
            mypdfviewer.fromAsset("075.pdf").load();
        }if (getitem.equals("Al-Insaan"))
        {
            mypdfviewer.fromAsset("076.pdf").load();
        }if (getitem.equals("Al-Mursalaat"))
        {
            mypdfviewer.fromAsset("077.pdf").load();
        }if (getitem.equals("An-Naba"))
        {
            mypdfviewer.fromAsset("078.pdf").load();
        }if (getitem.equals("An-Naazi'aat"))
        {
            mypdfviewer.fromAsset("079.pdf").load();
        }if (getitem.equals("Abasa"))
        {
            mypdfviewer.fromAsset("080.pdf").load();
        }if (getitem.equals("At-Takweer"))
        {
            mypdfviewer.fromAsset("081.pdf").load();
        }if (getitem.equals("Al-Infitar"))
        {
            mypdfviewer.fromAsset("082.pdf").load();
        }if (getitem.equals("Al-Mutaffifeen"))
        {
            mypdfviewer.fromAsset("083.pdf").load();
        }if (getitem.equals("Al-Inshiqaaq"))
        {
            mypdfviewer.fromAsset("084.pdf").load();
        }if (getitem.equals("Al-Burooj"))
        {
            mypdfviewer.fromAsset("085.pdf").load();
        }if (getitem.equals("At-Taariq"))
        {
            mypdfviewer.fromAsset("086.pdf").load();
        }if (getitem.equals("Al-A'laa"))
        {
            mypdfviewer.fromAsset("087.pdf").load();
        }if (getitem.equals("Al-Ghaashiyah"))
        {
            mypdfviewer.fromAsset("088.pdf").load();
        }if (getitem.equals("Al-Fajr"))
        {
            mypdfviewer.fromAsset("089.pdf").load();
        }if (getitem.equals("Al-Balad"))
        {
            mypdfviewer.fromAsset("090.pdf").load();
        }if (getitem.equals("Ash-Shams"))
        {
            mypdfviewer.fromAsset("091.pdf").load();
        }if (getitem.equals("Al-Layl"))
        {
            mypdfviewer.fromAsset("092.pdf").load();
        }if (getitem.equals("Ad-Dhuha"))
        {
            mypdfviewer.fromAsset("093.pdf").load();
        }if (getitem.equals("Ash-Sharh"))
        {
            mypdfviewer.fromAsset("094.pdf").load();
        }if (getitem.equals("At-Teen"))
        {
            mypdfviewer.fromAsset("095.pdf").load();
        }if (getitem.equals("Al-Alaq"))
        {
            mypdfviewer.fromAsset("096.pdf").load();
        }if (getitem.equals("Al-Qadr"))
        {
            mypdfviewer.fromAsset("097.pdf").load();
        }if (getitem.equals("Al-Bayyinahh"))
        {
            mypdfviewer.fromAsset("098.pdf").load();
        }if (getitem.equals("Az-Zalzalah"))
        {
            mypdfviewer.fromAsset("099.pdf").load();
        }if (getitem.equals("Al-'Aadiyaat"))
        {
            mypdfviewer.fromAsset("100.pdf").load();
        }if (getitem.equals("Al-Qaari'ah"))
        {
            mypdfviewer.fromAsset("101.pdf").load();
        }if (getitem.equals("At-Takaathur"))
        {
            mypdfviewer.fromAsset("102.pdf").load();
        }if (getitem.equals("Al-'Asr"))
        {
            mypdfviewer.fromAsset("103.pdf").load();
        }if (getitem.equals("Al-Humazah"))
        {
            mypdfviewer.fromAsset("104.pdf").load();
        }if (getitem.equals("Al-Feel"))
        {
            mypdfviewer.fromAsset("105.pdf").load();
        }if (getitem.equals("Quraysh"))
        {
            mypdfviewer.fromAsset("106.pdf").load();
        }if (getitem.equals("Al-Maa'oon"))
        {
            mypdfviewer.fromAsset("107.pdf").load();
        }if (getitem.equals("Al-Kawthar"))
        {
            mypdfviewer.fromAsset("108.pdf").load();
        }if (getitem.equals("Al-Kaafiroon"))
        {
            mypdfviewer.fromAsset("109.pdf").load();
        }if (getitem.equals("An-Nasr"))
        {
            mypdfviewer.fromAsset("110.pdf").load();
        }if (getitem.equals("Al-Masad"))
        {
            mypdfviewer.fromAsset("111.pdf").load();
        }if (getitem.equals("Al-Ikhlaas"))
        {
            mypdfviewer.fromAsset("112.pdf").load();
        }if (getitem.equals("Al-Falaq"))
        {
            mypdfviewer.fromAsset("113.pdf").load();
        }if (getitem.equals("An-Naas")) {
            mypdfviewer.fromAsset("114.pdf").load();
        }*/

    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(), Qraan.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}