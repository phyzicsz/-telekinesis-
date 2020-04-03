/*
 * Copyright 2020 phyzicsz <phyzics.z@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.phyzicsz.telekinesis.metric.repository;

import java.util.Arrays;

/**
 *
 * @author phyzicsz <phyzics.z@gmail.com>
 */
 public class StringsKey {
     private final static String NO_KEY = "NO_KEY";
    private final String[] labelValues;

    public String[] getLabelValues() {
        return labelValues;
    }
    
    public static String[] getNoKey(){
        return new String[]{NO_KEY};
    }
    
    public StringsKey(final String[] labelValues) {
        if(labelValues == null){
            this.labelValues = getNoKey();
        }else{
            this.labelValues = labelValues;
        }
    }

    @Override
    public boolean equals(final Object obj) {
      return Arrays.equals(labelValues, ((StringsKey) obj).labelValues);
    }

    @Override
    public int hashCode() {
      return Arrays.hashCode(labelValues);
    }
  }
