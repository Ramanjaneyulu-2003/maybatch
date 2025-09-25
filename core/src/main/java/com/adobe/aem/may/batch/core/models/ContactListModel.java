package com.adobe.aem.may.batch.core.models;

import org.apache.sling.api.resource.*;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import javax.annotation.PostConstruct;
import java.util.*;
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

public class ContactListModel {

  @SlingObject
  private Resource resource;

  private List<Contact> contacts;

  @PostConstruct
  protected void init() {
    contacts = new ArrayList<>();
    Resource parent = resource.getResourceResolver().getResource("/content/contacts");
    if (parent != null) {
      for (Resource c : parent.getChildren()) {
        ValueMap vm = c.getValueMap();
        contacts.add(new Contact(c.getName(),
          vm.get("name", ""),
          vm.get("email", ""),
          vm.get("mobile", "")
        ));
      }
    }
  }

  public List<Contact> getContacts() {
    return contacts;
  }

  public static class Contact {
    private String node, name, email, mobile;
    public Contact(String node, String name, String email, String mobile) {
      this.node = node; this.name = name; this.email = email; this.mobile = mobile;
    }
    public String getNode(){return node;}
    public String getName(){return name;}
    public String getEmail(){return email;}
    public String getMobile(){return mobile;}
  }
}

