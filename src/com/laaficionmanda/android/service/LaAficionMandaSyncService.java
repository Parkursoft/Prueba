package com.laaficionmanda.android.service;

import java.io.UnsupportedEncodingException;

import org.apache.http.HttpStatus;

import android.text.TextUtils;

import com.laaficionmanda.android.LaAficionMandaApp;
import com.laaficionmanda.android.service.RestClientHelper.RequestMethod;
import com.laaficionmanda.android.util.LogIt;

public class LaAficionMandaSyncService implements SyncService{

	@Override
	public String helloto(String name) {
		String result = null;
		String url = "http://www.aficionmanda.com/larcia/Service1.svc/helloto/" + name;
        RestClientHelper client = new RestClientHelper(url);

        try {
            client.execute(RequestMethod.GET);

            switch (client.getResponseCode()) {
            case HttpStatus.SC_OK:
                String response = client.getResponse();

                if (!TextUtils.isEmpty(response)) {
//                    Gson parser = new Gson();
//                    Type entityFieldChangeListType = new TypeToken<List<EntityFieldChange>>() {
//                    }.getType();
//
//                    result = parser.fromJson(response,
//                            entityFieldChangeListType);
                	result = client.getResponse();
                }

                break;
            }
        } catch (Exception e) {
            LogIt.e(this, e, e.getMessage());
        }

        return result;
	}

}
