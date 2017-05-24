# CDI Camel QuickStart

Create ci project:

    oc new-project ci

Create test project:

    
    oc new-project test

Create acceptance project:

    oc new project acceptance

Add ci/jenkins serviceaccount to test and acceptance

    TODO
    
Enable test to pull from ci:
        
        oc policy add-role-to-group system:image-puller system:serviceaccounts:test -n ci

Enable acceptance to pull from ci:

        
       oc policy add-role-to-group system:image-puller system:serviceaccounts:acceptance -n ci

Add image streams to ci:

        oc create -f fis-image-streams.json
        
        
Create the pipeline in ci. Make sure to provide a Maven Mirror. Maven central is also possible, but not default.

        oc project ci
        oc new-app -p MAVEN_MIRROR_URL=<insert maven mirror> -f split-pipeline.yaml
        
Pipeline can be trigger via git hooks or manually.