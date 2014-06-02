/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2014-04-15 19:10:39 UTC)
<<<<<<< HEAD
 * on 2014-06-02 at 14:43:24 UTC 
=======
 * on 2014-05-29 at 17:09:14 UTC 
>>>>>>> 3dcd2465fa1b0ccf19d0c52e5ab4c78015324544
 * Modify at your own risk.
 */

package com.travellog.userendpoint.model;

/**
 * Model definition for User.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the userendpoint. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class User extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime dateCreated;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Email email;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String firstName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
<<<<<<< HEAD
  private Key key;
=======
  private java.lang.Object id;
>>>>>>> 3dcd2465fa1b0ccf19d0c52e5ab4c78015324544

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String lastName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String password;

  /**
   * The value may be {@code null}.
   */
<<<<<<< HEAD
=======
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long userId;

  /**
   * The value may be {@code null}.
   */
>>>>>>> 3dcd2465fa1b0ccf19d0c52e5ab4c78015324544
  @com.google.api.client.util.Key
  private java.lang.String username;

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getDateCreated() {
    return dateCreated;
  }

  /**
   * @param dateCreated dateCreated or {@code null} for none
   */
  public User setDateCreated(com.google.api.client.util.DateTime dateCreated) {
    this.dateCreated = dateCreated;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Email getEmail() {
    return email;
  }

  /**
   * @param email email or {@code null} for none
   */
  public User setEmail(Email email) {
    this.email = email;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName firstName or {@code null} for none
   */
  public User setFirstName(java.lang.String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
<<<<<<< HEAD
  public Key getKey() {
    return key;
  }

  /**
   * @param key key or {@code null} for none
   */
  public User setKey(Key key) {
    this.key = key;
=======
  public java.lang.Object getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public User setId(java.lang.Object id) {
    this.id = id;
>>>>>>> 3dcd2465fa1b0ccf19d0c52e5ab4c78015324544
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getLastName() {
    return lastName;
  }

  /**
   * @param lastName lastName or {@code null} for none
   */
  public User setLastName(java.lang.String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPassword() {
    return password;
  }

  /**
   * @param password password or {@code null} for none
   */
  public User setPassword(java.lang.String password) {
    this.password = password;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
<<<<<<< HEAD
=======
  public java.lang.Long getUserId() {
    return userId;
  }

  /**
   * @param userId userId or {@code null} for none
   */
  public User setUserId(java.lang.Long userId) {
    this.userId = userId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
>>>>>>> 3dcd2465fa1b0ccf19d0c52e5ab4c78015324544
  public java.lang.String getUsername() {
    return username;
  }

  /**
   * @param username username or {@code null} for none
   */
  public User setUsername(java.lang.String username) {
    this.username = username;
    return this;
  }

  @Override
  public User set(String fieldName, Object value) {
    return (User) super.set(fieldName, value);
  }

  @Override
  public User clone() {
    return (User) super.clone();
  }

}
