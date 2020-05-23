package com.yxe.ktarmor.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.yxe.ktarmor.ui.account.data.response.HomeBean;
import com.yxe.ktarmor.common.api.API;
import com.yxe.ktarmor.R;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

import static com.bumptech.glide.load.resource.bitmap.VideoDecoder.FRAME_OPTION;

public class AdapterRecyclerViewTiny extends RecyclerView.Adapter<AdapterRecyclerViewTiny.MyViewHolder> {

    public static final String TAG = "AdapterRecyclerView";
//    int[] videoIndexs = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private Context context;
    private List<HomeBean.DataBean> urls;

    public AdapterRecyclerViewTiny(Context context) {
        this.context = context;
    }
    public void setUrls(List<HomeBean.DataBean> urls){
        this.urls = urls;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_videoview_tiny, parent,
                false));
        return holder;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        HomeBean.DataBean item = urls.get(position);
        String itemurl = API.BASE_URL+item.getUrl();
        Log.i(TAG, "onBindViewHolder [" + holder.jzvdStd.hashCode() + "] position=" + position);
        Log.i(TAG, "itemurl ==" + itemurl);
        if (holder.jzvdStd.getTag() != null)
            holder.jzvdStd = ((View) holder.jzvdStd.getTag()).findViewById(R.id.videoplayer);
        holder.jzvdStd.setUp(
                itemurl,0,item.getVideoTitle()
               );
//                Jzvd.SCREEN_NORMAL);
//        loadVideoScreenshot(holder.itemView.getContext(), itemurl,holder.jzvdStd.thumbImageView, 4000000L);
//        Glide.with(holder.jzvdStd.getContext()).load(netVideoBitmap
//                ).into(holder.jzvdStd.thumbImageView);
        Glide.with(context)
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(1000000)
                                .centerCrop()
//                                .error(R.mipmap.eeeee)//可以忽略
//                                .placeholder(R.mipmap.ppppp)//可以忽略
                )
                .load(itemurl)
                .into(holder.jzvdStd.thumbImageView);
    }

    @Override
    public int getItemCount() {
        return urls==null?0:urls.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        JZVideoPlayerStandard jzvdStd;

        public MyViewHolder(View itemView) {
            super(itemView);
            jzvdStd = itemView.findViewById(R.id.videoplayer);
            jzvdStd.setTag(itemView);
        }
    }
    /**
     *  服务器返回url，通过url去获取视频的第一帧
     *  Android 原生给我们提供了一个MediaMetadataRetriever类
     *  提供了获取url视频第一帧的方法,返回Bitmap对象
     *
     *  @param videoUrl
     *  @return
     */
    public static Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
//            retriever.setDataSource(videoUrl);
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }
    public static void loadVideoScreenshot(final Context context, String uri, ImageView imageView, long frameTimeMicros) {


        RequestOptions requestOptions = RequestOptions.frameOf(frameTimeMicros);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        requestOptions.set(FRAME_OPTION, MediaMetadataRetriever.OPTION_CLOSEST);
        requestOptions.transform(new BitmapTransformation() {
            @Override
            protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
                return toTransform;
            }
            @Override
            public void updateDiskCacheKey(MessageDigest messageDigest) {
                try {
                    messageDigest.update((context.getPackageName() + "RotateTransform").getBytes("utf-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Glide.with(context).load(uri).apply(requestOptions).into(imageView);
    }
}
