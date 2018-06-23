package codefathers.tripalert.database;

import android.os.AsyncTask;

 public class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

    private final TrackingModelDao mDao;

    PopulateDbAsync(AppDatabase db) {
        mDao = db.trackingModel();
    }

    @Override
    protected Void doInBackground(final Void... params) {
        mDao.deleteAll();
        mDao.insertAll(TrackingModel.populateData());
        return null;
    }
}
