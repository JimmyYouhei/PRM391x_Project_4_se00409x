/*
 * MIT License
 *
 * Copyright (c) 2019.  Jimmy Youhei (Quan Nguyen)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package vn.org.quantestyoutube2.prm391x_project_4_se00409x.event_for_eventbus;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.entity.VideoEntity;


// Wrapper class to wrap Context , List and RecyclerView for EventBus usage
public class ContextListRecyclerView {

    // 3 class to wrap
    private RecyclerView recyclerView;
    private Context context;
    private List<VideoEntity> videoEntityList;


    // Constructor
    public ContextListRecyclerView(Context context, List<VideoEntity> videoEntityList, RecyclerView recyclerView ) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.videoEntityList = videoEntityList;
    }

    // getter and setter
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<VideoEntity> getVideoEntityList() {
        return videoEntityList;
    }

    public void setVideoEntityList(List<VideoEntity> videoEntityList) {
        this.videoEntityList = videoEntityList;
    }
}
