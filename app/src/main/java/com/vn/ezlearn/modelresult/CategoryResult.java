package com.vn.ezlearn.modelresult;

import com.vn.ezlearn.models.ItemMenu;

import java.io.Serializable;
import java.util.List;

/**
 * Created by FRAMGIA\nguyen.duc.manh on 25/09/2017.
 */

public class CategoryResult implements Serializable {
    public Boolean success;
    public List<ItemMenu> data;
}
