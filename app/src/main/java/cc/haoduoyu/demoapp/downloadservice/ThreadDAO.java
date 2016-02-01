package cc.haoduoyu.demoapp.downloadservice;

import java.util.List;

/**
 * Created by XP on 2016/2/1.
 */
public interface ThreadDAO {

    void insertThread(ThreadInfo threadInfo);

    void deleteThread(String url, int thread_id);

    void updateThread(String url, int thread_id, int finished);

    List<ThreadInfo> getThreads(String url);

    boolean isExists(String url, int thread_id);
}