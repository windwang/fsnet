#!/bin/bash -x

# The target folder to install the documentation
REPORTS_DESTINATION=maven-reports/

# Create new reports
mvn clean site:stage

# Copy generated reports to the destination folder
cp -r target/staging/localhost/* $REPORTS_DESTINATION

# Go to the reports folder
pushd $REPORTS_DESTINATION

# Set the right property for each files of the generated report
for nonVersionnedFile in $(svn stat  | grep '\?' | cut -d ' ' -f 8-) ; do
    svn add $nonVersionnedFile
done

echo "ANALIZING CSS FILES"
find . -name "*.css" -exec svn propset svn:mime-type text/css {} \;
echo "ANALIZING JPEG FILES"
find . -name "*.jpg" -exec svn propset svn:mime-type image/jpeg {} \;
echo "ANALIZING GIF FILES"
find . -name "*.gif" -exec svn propset svn:mime-type image/gif {} \;
echo "ANALIZING PNG FILES"
find . -name "*.png" -exec svn propset svn:mime-type image/png {} \;
 echo "ANALIZING HTML FILES"
find . -name "*.html" -exec svn propset svn:mime-type text/html {} \;

# Return to the previous directory
popd