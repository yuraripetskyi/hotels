package com.ua.hotels.models.enums;

import com.ua.hotels.models.Hotel;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;


public enum Stars {
    ONE_STAR , TWO_STAR , THREE_STAR , FOUR_STAR , FIVE_STAR
}
