import 'dart:io';

import 'package:io/io.dart' as io;
import 'package:xml/xml.dart' as xml;

void main() {
  try {
    String unityExportPath = '${Directory.current.path}/android/unityExport';

    if (!Directory(unityExportPath).existsSync()) {
      print('The "unityExport" directory is missing!');
      return;
    }

    if (!Directory('$unityExportPath/unityLibrary').existsSync()) {
      return;
    }

    io.copyPathSync('$unityExportPath/launcher/src/main/res',
        '$unityExportPath/unityLibrary/src/main/res');

    Directory('$unityExportPath/launcher').deleteSync(recursive: true);

    for (FileSystemEntity entity in Directory(unityExportPath).listSync()) {
      try {
        entity.deleteSync();
      } catch (_) {}
    }

    io.copyPathSync('$unityExportPath/unityLibrary', '$unityExportPath');

    Directory('$unityExportPath/unityLibrary').deleteSync(recursive: true);

    File file = File('$unityExportPath/src/main/AndroidManifest.xml');
    String contents = file.readAsStringSync();
    xml.XmlDocument document = xml.parse(contents);
    document.rootElement.children.elementAt(1).children.removeAt(1);
    file.writeAsStringSync(document.toXmlString(pretty: true));
  } catch (e) {
    print(e);
  }
}
