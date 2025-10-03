from lxml import etree

def parse_xml(file_path):
    try:
        # Загрузка XML-файла
        tree = etree.parse(file_path)
        root = tree.getroot()

        # Определяем namespace (если есть)
        ns = {'ns': root.nsmap[None]} if None in root.nsmap else {}

        print("\nОбработка файла:", file_path)
        
        # Вывод информации о дронах
        print("=== Дроны ===")
        for drone in root.findall('ns:Drone', ns) if ns else root.findall('Drone'):
            print(f"ID: {drone.get('id')}")
            print(f"Модель: {drone.find('ns:Model', ns).text if ns else drone.find('Model').text}")
            print(f"Статус: {drone.find('ns:Status', ns).text if ns else drone.find('Status').text}")
            print()

        # Вывод информации о целях
        print("=== Цели ===")
        for target in root.findall('ns:Target', ns) if ns else root.findall('Target'):
            print(f"ID: {target.get('id')}")
            print(f"Название: {target.find('ns:Name', ns).text if ns else target.find('Name').text}")
            print(f"Тип: {target.find('ns:Type', ns).text if ns else target.find('Type').text}")
            coords = target.find('ns:Coordinates', ns) if ns else target.find('Coordinates')
            print(f"Координаты: {coords.find('ns:Latitude', ns).text if ns else coords.find('Latitude').text}, "
                  f"{coords.find('ns:Longitude', ns).text if ns else coords.find('Longitude').text}, "
                  f"{coords.find('ns:Altitude', ns).text if ns else coords.find('Altitude').text}")
            print()

        # Вывод информации о фотографиях
        print("=== Фотографии ===")
        for photo in root.findall('ns:Photo', ns) if ns else root.findall('Photo'):
            print(f"ID: {photo.get('id')}")
            print(f"ID дрона: {photo.get('droneID')}")
            print(f"ID цели: {photo.get('targetID')}")
            print(f"Дата/время: {photo.get('dateTime')}")
            print(f"Путь к файлу: {photo.get('filePath')}")
            metadata = photo.find('ns:PhotoMetadata', ns) if ns else photo.find('PhotoMetadata')
            print(f"Разрешение: {metadata.find('ns:Resolution', ns).text if ns else metadata.find('Resolution').text}")
            print(f"Размер файла: {metadata.find('ns:FileSize', ns).text if ns else metadata.find('FileSize').text}")
            print(f"Формат: {metadata.find('ns:Format', ns).text if ns else metadata.find('Format').text}")
            print()

    except Exception as e:
        print(f"Ошибка при обработке файла {file_path}: {str(e)}")

# Обработка файлов
parse_xml("example.xml")
parse_xml("example_with_xsd.xml")