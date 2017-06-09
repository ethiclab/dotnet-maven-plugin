# dotnet-maven-plugin

This plugin allows to modify .NET assembly version by following some conventions.

[![Build Status](https://travis-ci.org/ethiclab/dotnet-maven-plugin.svg?branch=master)](https://travis-ci.org/ethiclab/dotnet-maven-plugin)

[![codecov](https://codecov.io/gh/ethiclab/dotnet-maven-plugin/branch/master/graph/badge.svg)](https://codecov.io/gh/ethiclab/dotnet-maven-plugin)

Quick Start.
============

`````xml
  <build>
    <plugins>
      <plugin>
        <groupId>com.github.ethiclab</groupId>
        <artifactId>dotnet-maven-plugin</artifactId>
        <version>1.1.0</version>
        <extensions>true</extensions>
      </plugin>
    </plugins>
  </build>
  <pluginRepositories>
    <pluginRepository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
    </pluginRepository>
  </pluginRepositories>
`````

TODO: Add examples of usage.
