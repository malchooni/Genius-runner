package name.yalsooni.genius.paperfactory.definition;

/**
 * Created by ijyoon on 2017. 4. 19..
 */
public interface ExcelReadIdx {

    int TEMP_ID = 0;
    int TEMP_DIR = 1;
    int TEMP_TARGET = 2;

    String TARGET = "T";

    int TEMP_DATA_SUBJECT_IDX = 0;
    int TEMP_DATA_START_IDX = 1;

    int DATA_COLS_START_IDX = 3;
    int DATA_COLS_TARGET_IDX = DATA_COLS_START_IDX-1;
}
