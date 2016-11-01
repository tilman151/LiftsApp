package krok.lifts;

import android.provider.BaseColumns;

/**
 * Created by tilman on 28.10.16.
 */

public final class LiftsContract {

    private LiftsContract() {}

    public static class Lifts implements BaseColumns {

        public static final String TABLE_NAME = "lifts";
        public static final String COLLUMN_NAME_NAME = "name";
        public static final String COLLUMN_NAME_REPSCHEME = "repscheme";
        public static final String COLLUMN_NAME_COMPOUND = "compound";

    }

    public static class Maxes implements BaseColumns {

        public static final String TABLE_NAME = "maxes";
        public static final String COLLUMN_NAME_LIFT = "lift";
        public static final String COLLUMN_NAME_WEIGHT = "weight";

    }

    public static class Sets implements BaseColumns {

        public static final String TABLE_NAME = "sets";
        public static final String COLLUMN_NAME_LIFT = "lift";
        public static final String COLLUMN_NAME_WORKOUT = "workout";
        public static final String COLLUMN_NAME_ORDER = "ordering";
        public static final String COLLUMN_NAME_REPS = "reps";
        public static final String COLLUMN_NAME_WEIGHT = "weight";

    }

    public static class Workouts implements BaseColumns {

        public static final String TABLE_NAME = "workouts";
        public static final String COLLUMN_NAME_DATE = "date";
        public static final String COLLUMN_NAME_CYCLE = "cycle";
        public static final String COLLUMN_NAME_WEEK = "week";
        public static final String COLLUMN_NAME_DONE = "done";

    }

    public static class Cycles implements BaseColumns {

        public static final String TABLE_NAME = "cycles";
        public static final String COLLUMN_NAME_PRESS = "press";
        public static final String COLLUMN_NAME_DEAD = "dead";
        public static final String COLLUMN_NAME_BENCH = "bench";
        public static final String COLLUMN_NAME_SQUAT = "squat";

    }

}
