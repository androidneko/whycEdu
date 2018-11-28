package com.androidcat.acnet.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.androidcat.acnet.entity.Grade;
import com.androidcat.acnet.entity.OrderInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: FuelMore
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-8-4 18:17:15
 * add function description here...
 */
public class GradeListResponse extends BaseResponse {

    public List<Grade> content = new ArrayList<>();
}
