/*
 * Copyright (C) 2014-2016 LinkedIn Corp. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied.
 */

package gobblin.data.management.copy.replication;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import gobblin.dataset.FileSystemDataset;
import gobblin.util.HadoopUtils;

/**
 * {@link FileSystemDataset} wrapper class for {@link HadoopFsEndPoint}
 * @author mitu
 *
 */
public class HadoopFsEndPointDataset implements FileSystemDataset{
  
  private final HadoopFsEndPoint endPoint;
  private Path qualifiedDatasetRoot;
  
  public HadoopFsEndPointDataset(HadoopFsEndPoint endPoint){
    this.endPoint = endPoint;
    Configuration conf = HadoopUtils.newConfiguration();
    try {
      FileSystem fs = FileSystem.get(this.endPoint.getFsURI(), conf);
      qualifiedDatasetRoot =  fs.makeQualified(this.endPoint.getDatasetPath());
    } catch (IOException e1) {
      // ignored
      qualifiedDatasetRoot = this.endPoint.getDatasetPath();
    }
  }

  @Override
  public String datasetURN() {
    return this.qualifiedDatasetRoot.toString();
  }

  @Override
  public Path datasetRoot() {
    return this.qualifiedDatasetRoot;
  }
}
