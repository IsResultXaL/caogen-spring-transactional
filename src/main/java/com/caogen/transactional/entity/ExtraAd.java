package com.caogen.transactional.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author 康良玉
 * @Description 描述
 * @Create 2022-07-07 11:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "extra_ad")
public class ExtraAd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * <h2>提供 name 的构造函数</h2>
     * */
    public ExtraAd(String name) {
        this.name = name;
    }
}
