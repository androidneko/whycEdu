package com.androidcat.acnet.entity.response;

import com.androidcat.acnet.entity.Classroom;
import com.androidcat.acnet.entity.Grade;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: FuelMore
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-8-4 18:17:15
 * add function description here...
 */
public class ClassListResponse extends BaseResponse {

    public List<Classroom> content = new ArrayList<>();
}
