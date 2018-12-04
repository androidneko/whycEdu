package com.androidcat.acnet.entity.response;

import com.androidcat.acnet.entity.ClassMark;
import com.androidcat.acnet.entity.ScoreEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: FuelMore
 * Author: androidcat
 * Email:androidcat@126.com
 * Created at: 2017-8-4 18:17:15
 * add function description here...
 */
public class ScoreListResponse extends BaseResponse {

    public List<ScoreEntity> content = new ArrayList<>();
}
