/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.apache.eagle.security.oozie.parse;

import org.apache.eagle.dataproc.impl.storm.kafka.KafkaSourcedSpoutProvider;
import org.apache.eagle.datastream.ExecutionEnvironments;
import org.apache.eagle.datastream.storm.StormExecutionEnvironment;
import org.apache.eagle.security.oozie.parse.sensitivity.OozieResourceSensitivityDataJoinExecutor;

public class OozieAuditLogProcessorMain {
    public static void main(String[] args) throws Exception {
        StormExecutionEnvironment env = ExecutionEnvironments.getStorm(args);
        env.fromSpout(new KafkaSourcedSpoutProvider()).withOutputFields(1).nameAs("kafkaMsgConsumer")
                .flatMap(new OozieResourceSensitivityDataJoinExecutor())
                .alertWithConsumer("oozieSecurityLogEventStream", "oozieAuditLogAlertExecutor");
        env.execute();
    }
}