import java.io.FileNotFoundException

import sbt._
import Keys._
import de.heikoseeberger.sbtheader.HeaderPattern
import de.heikoseeberger.sbtheader.license.Apache2_0
import scalariform.formatter.preferences._
import com.typesafe.sbt.SbtScalariform
import sbtrelease._
import ReleaseTransformations._
import sbtrelease.ReleasePlugin._
import ReleaseStateTransformations._
import com.typesafe.sbt.pgp.PgpKeys

lazy val `scala-iso` =
  project
    .in(file("."))
    .enablePlugins(AutomateHeaderPlugin, GitVersioning)
    .settings(settings)
    .settings(publishSettings: _*)
    .settings(
      libraryDependencies ++= Seq(
        library.scalaCheck % Test,
        library.scalaTest  % Test
      )
    )

lazy val library =
  new {
    val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.13.4"
    val scalaTest  = "org.scalatest" %% "scalatest" % "3.3.0-SNAP3" % "test"
}

lazy val settings =
  commonSettings ++
  gitSettings ++
  headerSettings

lazy val commonSettings =
  Seq(
    organization := "systems.enliven",
    scalaVersion := "2.13.6",
    crossVersion := CrossVersion.binary,
    mappings.in(Compile, packageBin) +=
      baseDirectory.in(ThisBuild).value / "LICENSE" -> "LICENSE",
    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation",
      "-encoding", "utf8",
      "-feature",
      "-explaintypes",
      "-target:jvm-1.8",
      "-language:_",
      "-Ydelambdafy:method",
      "-Xcheckinit",
      "-Xfuture",
      "-Xlint",
      "-Xlint:-nullary-unit",
      "-Ywarn-unused",
      "-Ywarn-dead-code",
      "-Ywarn-value-discard"
    ),
    unmanagedSourceDirectories.in(Compile) :=
      Seq(scalaSource.in(Compile).value),
    unmanagedSourceDirectories.in(Test) :=
      Seq(scalaSource.in(Test).value),
    SbtScalariform.autoImport.scalariformPreferences := SbtScalariform.autoImport.scalariformPreferences.value
      .setPreference(AlignSingleLineCaseStatements, true)
      .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
      .setPreference(DoubleIndentClassDeclaration, true)
      .setPreference(RewriteArrowSymbols, true)
      .setPreference(AlignParameters, true)
      .setPreference(AlignArguments, true)
      .setPreference(DoubleIndentClassDeclaration, true)
      .setPreference(DanglingCloseParenthesis, Preserve),
    wartremoverWarnings ++= Warts.unsafe
)

lazy val publishSettings = Seq(
  /**
    * Do not pack sources in compile tasks.
    */
  Compile / doc / sources := Seq.empty,
  /**
    * Disabling Scala and Java documentation in publishing tasks.
    */
  Compile / packageDoc / publishArtifact := false,
  Test / packageDoc / publishArtifact := false,
  Test / packageBin / publishArtifact := true,
  Test / packageSrc / publishArtifact := true,
  publishConfiguration := publishConfiguration.value.withOverwrite(true),
  publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true),
  publishTo := Some(
    "Artifactory Realm".at(s"https://central.enliven.systems/artifactory/sbt-release/")
  ),
  credentials += Credentials(Path.userHome / ".sbt" / ".credentials.central"),
)

parallelExecution in Test := false
fork in Test := true
