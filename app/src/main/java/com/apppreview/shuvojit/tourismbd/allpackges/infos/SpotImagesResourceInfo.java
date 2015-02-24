package com.apppreview.shuvojit.tourismbd.allpackges.infos;



import android.content.Context;
import android.content.res.TypedArray;

import com.apppreview.shuvojit.tourismbd.R;

public class SpotImagesResourceInfo {

	private TypedArray typedArray;
	private int[] imageResources;
	private Context context;

	public SpotImagesResourceInfo(Context context) {
		this.context = context;

	}

	public int[] getSpotImageResources(String spotName) {
		switch (spotName) {
		case "Histroic Shat Gumbuj Mosque":
			typedArray = context.getResources().obtainTypedArray(
					R.array.historic_shat_gumbuj_mosque);
			break;
		case "Somapura Mahavihara":
			typedArray = context.getResources().obtainTypedArray(
					R.array.somapura_mahavihara);
			break;
		case "The Sundarbans":
			typedArray = context.getResources().obtainTypedArray(
					R.array.the_sundarbans);
			break;
		case "Cox\'s Bazar":
			typedArray = context.getResources().obtainTypedArray(
					R.array.coxs_bazar);
			break;
		case "Kuakata":
			typedArray = context.getResources().obtainTypedArray(
					R.array.kuakata);
			break;
		case "St. Martin\'s Island":
			typedArray = context.getResources().obtainTypedArray(
					R.array.st_martins_island);
			break;
		case "Patenga":
			typedArray = context.getResources().obtainTypedArray(
					R.array.patenga);
			break;
		case "Nijhum Dwip":
			typedArray = context.getResources().obtainTypedArray(
					R.array.nijhum_dwip);
			break;
		case "Bandarban":
			typedArray = context.getResources().obtainTypedArray(
					R.array.bandarban);
			break;
		case "Khagrachari":
			typedArray = context.getResources().obtainTypedArray(
					R.array.khagrachari);
			break;
		case "Rangamati":
			typedArray = context.getResources().obtainTypedArray(
					R.array.rangamati);
			break;
		case "Jaflong":
			typedArray = context.getResources().obtainTypedArray(
					R.array.jaflong);
			break;
		case "Srimangal":
			typedArray = context.getResources().obtainTypedArray(
					R.array.srimangal);
			break;
		case "Nilachal":
			typedArray = context.getResources().obtainTypedArray(
					R.array.nilachal);
			break;
		case "Nilgiri":
			typedArray = context.getResources().obtainTypedArray(
					R.array.nilgiri);
			break;
		case "Bhola Island":
			typedArray = context.getResources().obtainTypedArray(
					R.array.bhola_island);
			break;
		case "Hatiya":
			typedArray = context.getResources()
					.obtainTypedArray(R.array.hatiya);
			break;
		case "Kutubdia":
			typedArray = context.getResources().obtainTypedArray(
					R.array.kutubdia);
		case "Manpura Island":
			typedArray = context.getResources().obtainTypedArray(
					R.array.manpura_island);
			break;
		case "Sandwip":
			typedArray = context.getResources().obtainTypedArray(
					R.array.sandwip);
			break;
		case "Sonadia Island":
			typedArray = context.getResources().obtainTypedArray(
					R.array.sonadia_island);
			break;
		case "Bhawal National Park":
			typedArray = context.getResources().obtainTypedArray(
					R.array.bhawal_national_park);
			break;
		case "Lawachara National Park":
			typedArray = context.getResources().obtainTypedArray(
					R.array.lawachara_national_park);
			break;
		case "Bangabandhu Sheikh Mujib Safari Park":
			typedArray = context.getResources().obtainTypedArray(
					R.array.bangabandhu_sheikh_mujib_safari_park);
			break;
		case "Hum Hum":
			typedArray = context.getResources().obtainTypedArray(
					R.array.hum_hum);
			break;
		case "Madhabkunda":
			typedArray = context.getResources().obtainTypedArray(
					R.array.madhabkunda);
			break;
		case "Nafa-khum":
			typedArray = context.getResources().obtainTypedArray(
					R.array.nafa_khum);
			break;
		case "Jadipai waterfall":
			typedArray = context.getResources().obtainTypedArray(
					R.array.jadipai_waterfall);
			break;
		case "Jagaddala Mahavihara":
			typedArray = context.getResources().obtainTypedArray(
					R.array.jagaddala_mahavihara);
			break;
		case "Mahasthangarh":
			typedArray = context.getResources().obtainTypedArray(
					R.array.mahasthangarh);
			break;
		case "Mainamati":
			typedArray = context.getResources().obtainTypedArray(
					R.array.mainamati);
			break;
		case "Mosque City of Bagerhat":
			typedArray = context.getResources().obtainTypedArray(
					R.array.mosque_city_of_bagerhat);
			break;
		case "Sonargaon":
			typedArray = context.getResources().obtainTypedArray(
					R.array.sonargaon);
			break;
		case "Wari-Bateshwar":
			typedArray = context.getResources().obtainTypedArray(
					R.array.wari_bateshwar);
			break;
		case "Ahsan Manzil":
			typedArray = context.getResources().obtainTypedArray(
					R.array.ahsan_manzil);
			break;
		case "Bara Katra":
			typedArray = context.getResources().obtainTypedArray(
					R.array.bara_katra);
			break;
		case "Curzon Hall":
			typedArray = context.getResources().obtainTypedArray(
					R.array.curzon_hall);
			break;
		case "Jatiyo Sangshad Bhaban":
			typedArray = context.getResources().obtainTypedArray(
					R.array.jatiyo_sangshad_bhaban);
			break;
		case "Lalbhag Fort":
			typedArray = context.getResources().obtainTypedArray(
					R.array.lalbhag_fort);
			break;
		case "Northbrook Hall":
			typedArray = context.getResources().obtainTypedArray(
					R.array.northbrook_hall);
			break;
		case "Kantajew Temple":
			typedArray = context.getResources().obtainTypedArray(
					R.array.kantajew_temple);
			break;
		case "Buddha Dhatu Jadi":
			typedArray = context.getResources().obtainTypedArray(
					R.array.buddha_dhatu_jadi);
			break;
		case "Armenian Church":
			typedArray = context.getResources().obtainTypedArray(
					R.array.armenian_church);
			break;
		case "Jatiyo Smriti Soudho":
			typedArray = context.getResources().obtainTypedArray(
					R.array.jatiyo_smriti_soudho);
			break;
		case "Shaheed Minar":
			typedArray = context.getResources().obtainTypedArray(
					R.array.shaheed_minar);
			break;
		default:
			break;
		}
		imageResources = new int[typedArray.length()];
		for (int i = 0; i < typedArray.length(); i++) {
			imageResources[i] = typedArray.getResourceId(i, 0);
		}
		typedArray.recycle();
		return imageResources;
	}

}
