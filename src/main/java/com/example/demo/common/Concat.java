package com.example.demo.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Djh
 */
@Data
public class Concat<T1, T2> {

    private T1 obj1;

    private T2 obj2;
}
