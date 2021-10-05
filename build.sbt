import sbt.Tests.{Group, SubProcess}
import sbtrelease.ReleaseStateTransformations._
import sbtrelease.{versionFormatError, Version}

makePomConfiguration := makePomConfiguration.value.withConfigurations(
  Configurations.defaultMavenConfigurations
)

lazy val commonSettings = Seq(
  startYear := Some(2021),
  headerMappings := headerMappings.value + (HeaderFileType.scala -> HeaderCommentStyle.cStyleBlockComment),
  headerLicense := Some(
    HeaderLicense.Custom(
      """|MIT License
         |
         |Copyright (c) 2021 Enliven Systems Kft.
         |
         |Permission is hereby granted, free of charge, to any person obtaining a copy
         |of this software and associated documentation files (the "Software"), to deal
         |in the Software without restriction, including without limitation the rights
         |to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
         |copies of the Software, and to permit persons to whom the Software is
         |furnished to do so, subject to the following conditions:
         |
         |The above copyright notice and this permission notice shall be included in all
         |copies or substantial portions of the Software.
         |
         |THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
         |IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
         |FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
         |AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
         |LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
         |OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
         |SOFTWARE.
         |""".stripMargin
    )
  ),
  headerSources / excludeFilter := HiddenFileFilter || "*Excluded.scala",
  headerResources / excludeFilter := HiddenFileFilter || "*.xml",
  organizationName := "Enliven Systems Kft.",
  organization := "systems.enliven.iso",
  scalaVersion := "2.13.6",
  semanticdbEnabled := true,
  semanticdbVersion := scalafixSemanticdb.revision,
  addCompilerPlugin(scalafixSemanticdb),
  scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.5.0",
  scalafixScalaBinaryVersion := "2.13.6",
  scalacOptions ++= List(
    "-Yrangepos",
    "-encoding",
    "UTF-8",
    "-deprecation",
    "-feature",
    "-unchecked",
    "-Ywarn-unused",
    "-language:implicitConversions",
    "-language:postfixOps"
  ),
  releaseVersionBump := sbtrelease.Version.Bump.Next,
  releaseIgnoreUntrackedFiles := true,
  releaseVersion := {
    ver =>
      Version(ver)
        .map(_.withoutQualifier.string)
        .getOrElse(versionFormatError(ver))
  },
  releaseNextVersion := {
    ver =>
      Version(ver)
        .map(_.bump(releaseVersionBump.value).withoutQualifier.string)
        .getOrElse(versionFormatError(ver))
  },
  releaseProcess := Seq[ReleaseStep](
    inquireVersions,
    setReleaseVersion,
    commitReleaseVersion,
    setNextVersion,
    commitNextVersion,
    pushChanges
  ),
  fork := true,
  IntegrationTest / fork := true,
  Test / fork := true,
  Test / testForkedParallel := true,
  IntegrationTest / testForkedParallel := true,
  Global / concurrentRestrictions := Seq(Tags.limitAll(6)),
  Test / parallelExecution := true,
  Test / testGrouping := (Test / testGrouping).value.flatMap {
    group =>
      group.tests.map(
        test => Group(test.name, Seq(test), SubProcess(ForkOptions()))
      )
  },
  concurrentRestrictions := Seq(Tags.limit(Tags.ForkedTestGroup, 6)),
  Test / logLevel := Level.Info,
  publishConfiguration := publishConfiguration.value.withOverwrite(true),
  publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true),
  publishTo := Some(
    "Artifactory Realm".at(s"https://central.enliven.systems/artifactory/sbt-release/")
  ),
  credentials += Credentials(Path.userHome / ".sbt" / ".credentials.central"),
  resolvers ++= Seq(
    "Maven Central".at("https://repo1.maven.org/maven2/")
  ),
  Test / testOptions += Tests.Setup(
    () =>
      System.setProperty(
        "log4j.configuration",
        s"file:///${baseDirectory.value.getAbsolutePath}/../common/src/test/resources/log4j2.properties"
      )
  ),
  Test / packageBin / publishArtifact := true,
  Test / packageSrc / publishArtifact := true,
  Compile / packageDoc / publishArtifact := false,
  libraryDependencies ++= Seq(
    "org.scalacheck" %% "scalacheck" % "1.15.4",
    "org.scalatest" %% "scalatest" % "3.3.0-SNAP3" % "test"
  )
)

lazy val `scala-iso` =
  project
    .in(file("."))
    .settings(commonSettings)
