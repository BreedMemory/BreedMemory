/**
 * 项目名称：工具库 <br/>
 * 文件名称: CacheDataDAO.java <br/>
 * <p/>
 * Created by 谌珂 on 2016/2/17.  <br/>
 */
package com.uuzz.android.util.database.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Message;
import android.support.annotation.Nullable;

import com.uuzz.android.R;
import com.uuzz.android.util.ContextUtils;
import com.uuzz.android.util.ObservableTag;
import com.uuzz.android.util.database.DataBasesUtil;
import com.uuzz.android.util.database.entity.CacheDataEntity;

import java.util.List;

/**
 * 项目名称：工具库 <br/>
 * 类  名: CacheDataDAO <br/>
 * 类描述: 缓存数据到数据库的工具类<br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/2/17 <br/>
 * @author 谌珂 <br/>
 */
public class CacheDataDAO extends AbstractDAO<CacheDataEntity> {

    private static CacheDataDAO mCacheDataDAO;

    private CacheDataDAO(SQLiteDatabase db) {
        super(db);
    }

    /**
     * 描 述：获取CacheDataDAO实例<br/>
     * 作 者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/2/17 注释 <br/>
     */
    public synchronized static CacheDataDAO getInstance(@Nullable Context context) {
        if(mCacheDataDAO == null) {
            mCacheDataDAO = new CacheDataDAO(DataBasesUtil.getDb(context));
        }
        return mCacheDataDAO;
    }

    @Override
    public Class getBeanClass() {
        return CacheDataEntity.class;
    }

    @Override
    public String getTableName() {
        return "c_cache_data";
    }

    /**
     * 描 述：根据class获取缓存数据对象<br/>
     * 作 者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/2/17 注释 <br/>
     * @param userId 用户id
     * @param name 接口请求参数的md5
     * @return 缓存数据类
     */
    public CacheDataEntity getCacheDataModle(String userId, String name) {
        List<CacheDataEntity> result = select(null, "c_user_id = ? and c_name = ?", new String[]{userId, name},
                null, null, null, null);
        if(result != null && result.size() == 1) {
            return result.get(0);
        } else {
            return null;
        }
    }

    /**
     * 描 述：返回缓存的数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/2/24 注释 <br/>
     * @param userId 用户id
     * @param name 接口请求参数的md5
     * @return 需要的缓存数据对象
     */
    public CacheDataEntity getCacheData(String userId, String name) {
        try{
            return getCacheDataModle(userId, name);
        } catch (Exception e) {
            logger.w("Obtain cached data failed", e);
            return null;
        }
    }

    /**
     * 描 述：异步获取缓存数据并发布给监听者对象<br/>
     * 作 者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/2/24 注释 <br/>
     * @param userId 用户id
     * @param name 接口请求参数的md5
     */
    public void getCacheDataAsync(final String userId, final String name) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                CacheDataEntity data = getCacheData(userId, name);
                if(data == null) {
                    return;
                }

                Message msg = Message.obtain();
                msg.what = ObservableTag.CACHE_DATA;
                msg.obj = data;
                setChanged();
                notifyObservers(msg);
            }
        });
    }

    /**
     * 描 述：更新缓存数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/2/17 注释 <br/>
     * @param userId 用户id
     * @param name 接口请求参数的md5
     * @param data 缓存的数据内容的json串
     */
    public void updateCacheData(String userId, String name, String data) {
        update(new CacheDataEntity(name, data, userId), "c_user_id = ? and c_name = ?", new String[]{userId, name}, false);
    }

    /**
     * 描 述：插入一掉接口数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/1 <br/>
     * @param context 上下文
     * @param name 接口请求参数的md5
     * @param data 接口返回的数据
     */
    public void insertCacheDate(Context context, String name, String data) {
        String userId = ContextUtils.getSharedString(context, R.string.shared_preference_user_id);
        //缓存接口数据
        if(getCacheDataModle(userId, name) != null) {      //如果数据库中存在这条数据则更新
            updateCacheData(userId, name, data);
        } else {                                            //否则插入一条新数据
            // DONE: 谌珂 2016/2/17 插入缓存数据
            insert(new CacheDataEntity(name, data, userId), false);
        }
    }

    /**
     * 描 述：插入一掉接口数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/1 <br/>
     * @param context 上下文
     * @param name 接口请求参数的md5
     * @param data 接口返回的数据
     */
    public void insertCacheDateAsync(final Context context, final String name, final String data) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                insertCacheDate(context, name, data);
            }
        });
    }
}
