# CDI Camel QuickStart

Create ci project:

    oc new-project ci

Create test project:

    oc new-project test

Create acceptance project:

    oc new project acceptance


Add image streams to ci:

    oc create -f fis-image-streams.json -n ci
        
Create the pipeline in ci. Make sure to provide a Maven Mirror. Maven central is also possible, but not default.

    oc project ci
    oc new-app -p MAVEN_MIRROR_URL=<NEXUS_URL> -f split-pipeline.yaml
        
Add ci/jenkins serviceaccount to test and acceptance

    oc policy add-role-to-user edit system:serviceaccount:ci:jenkins -n test
    oc policy add-role-to-user edit system:serviceaccount:ci:jenkins -n acceptance
    
Enable test and acceptance to pull images from ci namespace:
        
    oc policy add-role-to-group system:image-puller system:serviceaccounts:test -n ci
    oc policy add-role-to-group system:image-puller system:serviceaccounts:acceptance -n ci
       
Pipeline can be trigger via git hooks or manually. 