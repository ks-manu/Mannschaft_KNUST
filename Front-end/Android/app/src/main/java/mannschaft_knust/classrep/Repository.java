package mannschaft_knust.classrep;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


class Repository {
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("ClassRep.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    WebService webService = retrofit.create(WebService.class);

}
