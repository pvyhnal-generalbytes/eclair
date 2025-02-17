/*
 * Copyright 2019 ACINQ SAS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.acinq.eclair.db

import fr.acinq.bitcoin.Crypto.PublicKey
import fr.acinq.bitcoin.{ByteVector32, Satoshi}
import fr.acinq.eclair.ShortChannelId
import fr.acinq.eclair.wire.{ChannelAnnouncement, ChannelUpdate, NodeAnnouncement}

trait NetworkDb {

  def addNode(n: NodeAnnouncement)

  def updateNode(n: NodeAnnouncement)

  def getNode(nodeId: PublicKey): Option[NodeAnnouncement]

  def removeNode(nodeId: PublicKey)

  def listNodes(): Seq[NodeAnnouncement]

  def addChannel(c: ChannelAnnouncement, txid: ByteVector32, capacity: Satoshi)

  def removeChannel(shortChannelId: ShortChannelId) = removeChannels(Seq(shortChannelId))

  /**
    * This method removes channel announcements and associated channel updates for a list of channel ids
    *
    * @param shortChannelIds list of short channel ids
    */
  def removeChannels(shortChannelIds: Iterable[ShortChannelId])

  def listChannels(): Map[ChannelAnnouncement, (ByteVector32, Satoshi)]

  def addChannelUpdate(u: ChannelUpdate)

  def updateChannelUpdate(u: ChannelUpdate)

  def listChannelUpdates(): Seq[ChannelUpdate]

  def addToPruned(shortChannelIds: Iterable[ShortChannelId]): Unit

  def removeFromPruned(shortChannelId: ShortChannelId)

  def isPruned(shortChannelId: ShortChannelId): Boolean

  def close(): Unit

}
