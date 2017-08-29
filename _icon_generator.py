# coding:utf-8
import datetime, time, os, sys
from PIL import Image

from watchdog.events import FileSystemEventHandler
from watchdog.observers import Observer

base_dir = 'app/src/main/res/mipmap-xxxhdpi/'

img_types = [
    [0.75, 'app/src/main/res/mipmap-xxhdpi/'],
    [0.5,  'app/src/main/res/mipmap-xhdpi/'],
    [0.375,  'app/src/main/res/mipmap-hdpi/'],
    [0.25,  'app/src/main/res/mipmap-mdpi/']
]


def resize_imgs(img_filename):

    try:
        img_xxxhdmi = Image.open(base_dir + img_filename, 'r')
        img_w, img_h = img_xxxhdmi.size
        print('resize img : ' + img_filename + ' __ ' + str(img_w) + ',' + str(img_h))
        for img_type in img_types:
            img_xxxhdmi.thumbnail((img_w * img_type[0], img_h * img_type[0]), Image.ANTIALIAS)
            img_xxxhdmi.save(img_type[1] + img_filename, 'PNG', quality=100, optimize=True)

    except IOError:
        print('delete img : ' + img_filename)
        for img_type in img_types:
            try:
                os.remove(img_type[1] + img_filename)
            except:
                print("File not found : " + img_filename)


def match(path):
    return path.endswith('.png')


class ChangeHandler(FileSystemEventHandler):
    def on_any_event(self, event):

        if event.is_directory:
            return
        if match(event.src_path):
            resize_imgs(os.path.basename(event.src_path))


if __name__ in '__main__':

    while 1:
        event_handler = ChangeHandler()
        observer = Observer()
        observer.schedule(event_handler, 'app/src/main/res/mipmap-xxxhdpi/', recursive=True)
        observer.start()
        try:
            while True:
                time.sleep(1)
        except KeyboardInterrupt:
            observer.stop()
        observer.join()


