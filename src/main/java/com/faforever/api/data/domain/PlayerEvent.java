package com.faforever.api.data.domain;

import com.yahoo.elide.annotation.Include;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "player_events")
@Include(rootLevel = true, type = "player_event")
public class PlayerEvent {

  private int id;
  private int playerId;
  private EventDefinition eventDefinition;
  private int count;
  private Timestamp createTime;
  private Timestamp updateTime;

  @Id
  @Column(name = "id")
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Basic
  @Column(name = "player_id")
  public int getPlayerId() {
    return playerId;
  }

  public void setPlayerId(int playerId) {
    this.playerId = playerId;
  }

  @ManyToOne
  @JoinColumn(name = "event_id", updatable = false, insertable = false)
  public EventDefinition getEventDefinition() {
    return eventDefinition;
  }

  public void setEventDefinition(EventDefinition eventDefinition) {
    this.eventDefinition = eventDefinition;
  }

  @Basic
  @Column(name = "count")
  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  @Basic
  @Column(name = "create_time")
  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  @Basic
  @Column(name = "update_time")
  public Timestamp getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Timestamp updateTime) {
    this.updateTime = updateTime;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, playerId, eventDefinition, count, createTime, updateTime);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PlayerEvent that = (PlayerEvent) o;
    return id == that.id &&
        playerId == that.playerId &&
        count == that.count &&
        Objects.equals(eventDefinition, that.eventDefinition) &&
        Objects.equals(createTime, that.createTime) &&
        Objects.equals(updateTime, that.updateTime);
  }
}
