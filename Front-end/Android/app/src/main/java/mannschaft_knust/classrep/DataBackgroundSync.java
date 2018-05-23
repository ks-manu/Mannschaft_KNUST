package mannschaft_knust.classrep;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class DataBackgroundSync extends JobService {
    DataRepository dataRepository;

    public boolean onStartJob(JobParameters parameters){
        dataRepository = new DataRepository(this);
        dataRepository.updateCoursePosts();
        dataRepository.updateCoursePosts();
        return true;
    }

    public boolean onStopJob(JobParameters parameters){
        return false;
    }
}
