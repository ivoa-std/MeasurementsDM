# Purpose

This is a workspace area for the development of the data model and subsequent generation
of the various products derived from the vo-dml/XML representation of the model.

Since there are many paths for generating the data model documents, the products themselves
are stored in the other directories of this repository, and no software is installed in this
workspace.  Instead, we provide the instructions for duplicating the workflow used to create
the current products.

The current workflow uses the [VO-DML toolkit utilities](https://github.com/ivoa/vo-dml).

Other utilities may also be used (e.g. for example file generation ).
If so, this space should provide the instructions for replicating the work, without including the code itself.


# VO-DML Toolkit plugin

The files included here are those necessary to execute the VO-DML Tookit.
This toolkit provides a set of tasks for generating and translating vo-dml/XML files.

For details on the toolkit, please see the "[Using the VO-DML Gradle Plugin](https://github.com/ivoa/vo-dml/tree/master/tools)"
page of the [VO-DML repository](https://github.com/ivoa/vo-dml).

## Get Started

Clone this repository
This is a command-line system

`git clone --recurse-submodules https://github.com/ivoa-std/MeasurementDM.git`
    
## Setup Environment

### Dependencies

The toolkit has 3 dependencies
* gradle version 8
* JDK 17
* graphviz

The following are meant to aid in getting an appropriate environment established

* **toolkit_environment.yml**: 

    This file can be used to create a conda environment with the appropriate packages (requires conda).

    `conda env create --file=toolkit_environment.yml`
    `conda activate vodml-tk`

* **JAVA_HOME**: 

    I found that setting this helped find the conda installed java over the system install.
    
    `setenv JAVA_HOME $CONDA_PREFIX/lib/jvm`

### Toolkit Resource files

Pulling from [VO-DML Tools Guide](https://ivoa.github.io/vo-dml/QuickStart/), with particulars for this model.

* **build.gradle.kts**

    The gradle bridge to the vo-dml toolkit, allows users to customize the configuration and register additional tasks.
    * vodmlDir: set to location of the vo-dml/xml file:  vodmlDir.set(file("../vo-dml"))
    * vodslDir: set to location of the vo-dml/dsl file:  vodslDir.set(file("../model"))
    * bindingFiles: set to location of binding file(s)

* **settings.gradle.kts**

    gradle support
    * set rootProject.name

* **meas.vo-dml.binding.xml**

    Binding file, contents map to description in the VO-DML Tools Guide (link above).
    In this repository, it is located coincident with the vo-dml/xml file it relates to (ie: in the vodmlDir).

# Workflow
The basic premise is to have
* the source model description in VODSL format for readability and easy modification, including difference checks in the Git repository
    * the final product is stored in the 'model' directory
* use the toolkit to convert this to vo-dml/xml, the normative definition of the data model
    * this product is stored in the 'vo-dml' directory
* use the toolkit to convert the vo-dml/xml file to various products
    * HTML documentation for the model
    * LaTeX document for generating the PDF
* diagrams for the PDF documentation are generated using the Modelio modeling tool (v3.7).
    NOTE: previous iterations of the model had exported the model in full from Modelio to XMI format and
    used the XSLT translation scripts (available through the Toolkit) to translate to VO-DML/XML.  This
    process can be combersome and the XSLT scripts must be tailored to specific modeling tool and version thereof.


# Running Tasks
The toolkit contains several tasks which can be used to generate and validate VO-DML/XML files, or translate them into
various other formats or to code.  These instructions are focused on the tasks used to create and validate the
VO-DML/XML files and generation of the standard HTML documentation.

* ```%> gradle UMLToVODML```
  For models developed fully using a modeling tool (e.g. Modelio UML tool) and exported to UML-2.4.1 format.
  This command translates the XMI file into the VO-DML/XML representation.

    * input, output, and translation script are specified with the task registration in build.gradle.kts
    * the task locates the xmi file, and executes the specified translation script
    * output vo-dml/xml is generated in the local directory.

* ```%> gradle vodmlValidate```
  Runs the VO-DML validation utility on the vo-dml/xml file.

    * locates the vo-dml/XML file using the information contained in build.gradle.kts
    * executes the validation utility
    * output is echoed to the screen

* ```%> gradle vodmlDoc```
  Runs a utility to generate HTML (and Tex) representation of the model.
  Note:  This model does not use the Tex format output from this utility for the actual PDF document.
  
    * locates the vo-dml/XML file using the information contained in build.gradle.kts
    * executes the translation task
    * output is written to ./build/generated/docs/vodml


* Schema Generation
