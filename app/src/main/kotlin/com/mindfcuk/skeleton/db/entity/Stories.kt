/*******************************************************************************
 * Copyright 2018 Ujjwal Singh, Sumit Pareek, Neeraj Sharma
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/

package com.mindfcuk.skeleton.db.entity

import android.arch.persistence.room.Entity
import com.mindfcuk.skeleton.db.TABLE_STORIES
import java.lang.invoke.ConstantCallSite

/**
 * Created by Ujjwal on 03/01/18.
 */

@Entity(tableName = TABLE_STORIES, primaryKeys = arrayOf("id"))
class Stories ( var by: String? = null,
                var descendants: Int = 0,
                var id: Int = 0,
                var kids: ArrayList<Int>? = null,
                var score: Int = 0,
                var time: Int = 0,
                var title: String? = null,
                var type: String? = null,
                var url: String? = null
)